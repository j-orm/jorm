package dev.jorm.cli.commands;

import dev.jorm.cli.config.ConfigResolver;
import dev.jorm.cli.output.Printer;
import dev.jorm.cli.output.Spinner;
import dev.jorm.cli.util.ExceptionUtils;
import dev.jorm.db.ConnectionManager;
import dev.jorm.db.MigrationRunner;
import dev.jorm.generator.SqlGenerator;
import dev.jorm.parser.JormLexer;
import dev.jorm.parser.JormParser;
import dev.jorm.parser.JormVisitorImpl;
import dev.jorm.core.model.SchemaModel;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import picocli.CommandLine.Command;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

@Command(name = "dev", description = "Generates and applies an SQL migration script based on schema.jorm")
public class MigrateDevCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        Spinner spinner = new Spinner("Preparing migration...");
        try {
            File schemaFile = new File(".jorm/schema.jorm");
            if (!schemaFile.exists()) {
                Printer.error("schema.jorm file not found. Run 'jorm init' first.");
                return 1;
            }
            
            spinner.start();

            String schemaContent = Files.readString(Path.of(schemaFile.getPath()));
            
            // Parse
            JormLexer lexer = new JormLexer(CharStreams.fromString(schemaContent));
            JormParser parser = new JormParser(new CommonTokenStream(lexer));
            JormParser.SchemaContext tree = parser.schema();
            
            JormVisitorImpl visitor = new JormVisitorImpl();
            SchemaModel model = visitor.visitSchema(tree);

            File migrationsDir = new File(".jorm/migrations");

            // Generate SQL
            SqlGenerator sqlGenerator = new SqlGenerator(model, migrationsDir);
            sqlGenerator.generate();

            Printer.info("Starting migrations execution...");
            
            // Try to get real connection
            ConnectionManager connManager;
            try {
                connManager = ConfigResolver.resolve(model);
            } catch (Exception ex) {
                spinner.stopWithError(ex.getMessage());
                return 1;
            }
            
            MigrationRunner runner = new MigrationRunner(connManager);
            runner.runMigrations(migrationsDir);

            spinner.stop("Migration dev completed successfully!");
            return 0;
        } catch (Exception e) {
            if (spinner != null) {
                spinner.stopWithError("Erro ao gerar/aplicar a migração. " + ExceptionUtils.userMessage(e));
            } else {
                Printer.error("Erro ao gerar/aplicar a migração. " + ExceptionUtils.userMessage(e));
            }
            ExceptionUtils.maybePrintDebug(e);
            return 1;
        }
    }
}

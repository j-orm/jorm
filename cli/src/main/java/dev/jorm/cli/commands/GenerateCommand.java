package dev.jorm.cli.commands;

import dev.jorm.cli.output.Printer;
import dev.jorm.cli.output.Spinner;
import dev.jorm.generator.ClientGenerator;
import dev.jorm.generator.JavaGenerator;
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

@Command(name = "generate", description = "Generates Java code from schema.jorm")
public class GenerateCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        Spinner spinner = new Spinner("Generating code...");
        try {
            File schemaFile = new File(".jorm/schema.jorm");
            if (!schemaFile.exists()) {
                Printer.error("schema.jorm file not found in the .jorm directory.");
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

            String outputDirPath = "src/main/java/generated";
            String packageName = "com.myapp.db";

            if (model.config() != null) {
                for (SchemaModel.ConfigEntry entry : model.config()) {
                    if ("output".equals(entry.key())) {
                        outputDirPath = entry.value();
                    } else if ("package".equals(entry.key())) {
                        packageName = entry.value();
                    }
                }
            }

            File outputDir = new File(outputDirPath);
            if (!outputDir.exists() && !outputDir.mkdirs()) {
                spinner.stopWithError("Could not create output directory.");
                return 1;
            }

            // Generate
            JavaGenerator generator = new JavaGenerator(model, outputDir, packageName);
            generator.generate();

            ClientGenerator clientGenerator = new ClientGenerator(model, outputDir, packageName);
            clientGenerator.generate();

            spinner.stop("Code successfully generated in " + outputDir.getPath());
            return 0;
        } catch (Exception e) {
            if (spinner != null) {
                spinner.stopWithError("Error generating code: " + e.getMessage());
            } else {
                Printer.error("Error generating code: " + e.getMessage());
            }
            return 1;
        }
    }
}

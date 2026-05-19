package dev.jorm.cli.commands;

import dev.jorm.cli.output.Printer;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;

@Command(name = "init", description = "Initializes a new Jorm project in the current directory")
public class InitCommand implements Callable<Integer> {

    @Option(names = {"-d", "--database"}, description = "Database type (postgresql, mysql, sqlite)", defaultValue = "postgresql")
    private String database;

    @Option(names = {"-p", "--package"}, description = "Java package for the generated code", defaultValue = "com.myapp.db")
    private String packageName;

    @Option(names = {"-o", "--output"}, description = "Output directory for the generated code", defaultValue = "src/main/java/generated")
    private String output;

    @Override
    public Integer call() {
        try {
            Printer.info("Initializing Jorm project...");

            File jormDir = new File(".jorm");
            if (!jormDir.exists() && jormDir.mkdirs()) {
                Printer.success("Directory .jorm created.");
            }

            File migrationsDir = new File(".jorm/migrations");
            if (!migrationsDir.exists() && migrationsDir.mkdirs()) {
                Printer.success("Migrations directory created.");
            }

            File schemaFile = new File(".jorm/schema.jorm");
            if (!schemaFile.exists()) {
                String initialSchema = String.format("""
                        // -----------------------------------------------------------------------------
                        // Database Connection Config
                        // -----------------------------------------------------------------------------
                        // The CLI automatically tries to discover your database using this priority:
                        // 1. DATABASE_URL environment variable
                        // 2. .env file in the root directory (DATABASE_URL=...)
                        // 3. application.properties or application.yml (spring.datasource.url)
                        // 4. docker-compose.yml (extracting ports and credentials)
                        // 5. database.url inside this config block
                        // -----------------------------------------------------------------------------
                        config {
                            database = "%s"
                            output = "%s"
                            package = "%s"
                            // Uncomment to configure connection manually here:
                            // database.url = "jdbc:postgresql://localhost:5432/mydb"
                            // database.username = "postgres"
                            // database.password = "password"
                        }
                        
                        model User {
                            id Int @id @autoincrement
                            name String
                            email String @unique
                            posts Post[]
                        }
                        
                        model Post {
                            id Int @id @autoincrement
                            title String
                            published Boolean
                        }
                        """, database, output, packageName);
                Files.writeString(Path.of(schemaFile.getPath()), initialSchema);
                Printer.success("Initial schema.jorm file created successfully.");
            } else {
                Printer.warn("The schema.jorm file already exists. No changes were made.");
            }

            Printer.print("\n@|bold,green Jorm project initialized successfully!|@");
            Printer.print("You can now edit the .jorm/schema.jorm file and run @|bold jorm generate|@");
            
            return 0;
        } catch (IOException e) {
            Printer.error(e.getMessage());
            return 1;
        }
    }
}

package dev.jorm.cli.commands;

import dev.jorm.cli.config.ConfigResolver;
import dev.jorm.cli.output.Printer;
import dev.jorm.cli.output.Spinner;
import dev.jorm.db.ConnectionManager;
import dev.jorm.db.MigrationRunner;
import picocli.CommandLine.Command;

import java.io.File;
import java.util.concurrent.Callable;

@Command(name = "deploy", description = "Applies all pending migrations to the production database")
public class MigrateDeployCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        Spinner spinner = new Spinner("Deploying migrations to production...");
        try {
            File migrationsDir = new File(".jorm/migrations");
            if (!migrationsDir.exists() || !migrationsDir.isDirectory()) {
                Printer.error("No migrations directory found.");
                return 1;
            }

            spinner.start();
            
            ConnectionManager connManager;
            try {
                connManager = ConfigResolver.resolve(null);
            } catch (Exception ex) {
                spinner.stopWithError(ex.getMessage());
                return 1;
            }

            MigrationRunner runner = new MigrationRunner(connManager);
            runner.runMigrations(migrationsDir);

            spinner.stop("Migrate deploy completed successfully!");
            return 0;
        } catch (Exception e) {
            if (spinner != null) {
                spinner.stopWithError("Error deploying migrations: " + e.getMessage());
            } else {
                Printer.error("Error deploying migrations: " + e.getMessage());
            }
            return 1;
        }
    }
}

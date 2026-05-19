package dev.jorm.cli.commands;

import dev.jorm.cli.config.ConfigResolver;
import dev.jorm.cli.output.Printer;
import dev.jorm.db.ConnectionManager;
import dev.jorm.db.MigrationRunner;
import picocli.CommandLine.Command;

import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

@Command(name = "status", description = "Displays the current status of migrations")
public class MigrateStatusCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        Printer.info("Checking migration status...");
        try {
            ConnectionManager connManager = ConfigResolver.resolve(null);
            MigrationRunner runner = new MigrationRunner(connManager);
            
            List<String> applied = runner.getAppliedMigrationsPublic();
            
            File migrationsDir = new File(".jorm/migrations");
            if (!migrationsDir.exists() || !migrationsDir.isDirectory()) {
                Printer.warn("No local migrations directory found.");
                return 0;
            }
            
            File[] files = migrationsDir.listFiles((dir, name) -> name.endsWith(".sql"));
            if (files == null || files.length == 0) {
                Printer.info("No local migrations found.");
                return 0;
            }
            
            Printer.print("\n@|bold,underline Migration Status:|@\n");
            
            for (File file : files) {
                String name = file.getName();
                if (applied.contains(name)) {
                    Printer.print("  @|green [APPLIED]|@ " + name);
                } else {
                    Printer.print("  @|yellow [PENDING]|@ " + name);
                }
            }
            
            Printer.print("");
            return 0;
        } catch (Exception e) {
            Printer.error("Could not check status: " + e.getMessage());
            return 1;
        }
    }
}

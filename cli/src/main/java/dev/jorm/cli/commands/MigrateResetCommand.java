package dev.jorm.cli.commands;

import dev.jorm.cli.config.ConfigResolver;
import dev.jorm.cli.output.Printer;
import dev.jorm.cli.output.Spinner;
import dev.jorm.db.ConnectionManager;
import picocli.CommandLine.Command;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import java.util.concurrent.Callable;

@Command(name = "reset", description = "Reverts all applied migrations, clearing the database (for development only)")
public class MigrateResetCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        Printer.warn("This will DROP all tables and data in your database!");
        System.out.print("Are you sure you want to continue? (y/N): ");
        
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        
        if (!"y".equalsIgnoreCase(input.trim()) && !"yes".equalsIgnoreCase(input.trim())) {
            Printer.info("Reset cancelled.");
            return 0;
        }

        Spinner spinner = new Spinner("Resetting database...");
        try {
            spinner.start();
            ConnectionManager connManager = ConfigResolver.resolve(null);
            
            try (Connection conn = connManager.getConnection();
                 Statement stmt = conn.createStatement()) {
                 
                // This is a naive reset for PostgreSQL public schema
                // A complete reset would need to know the dialect to DROP everything
                // For MVP we just drop public schema in PG, or we assume a naive drop.
                String url = conn.getMetaData().getURL();
                if (url.contains("postgresql")) {
                    stmt.execute("DROP SCHEMA public CASCADE; CREATE SCHEMA public;");
                } else if (url.contains("mysql")) {
                    // Not trivial without knowing DB name, assuming DROP DATABASE and CREATE
                    // This is risky, so we just clear _jorm_migrations for now as a fallback
                    Printer.warn("Full reset on MySQL requires manual drop. Clearing _jorm_migrations table only.");
                    stmt.execute("DROP TABLE IF EXISTS _jorm_migrations");
                }
            }
            
            spinner.stop("Database reset successfully!");
            return 0;
        } catch (Exception e) {
            if (spinner != null) {
                spinner.stopWithError("Error resetting database: " + e.getMessage());
            } else {
                Printer.error("Error resetting database: " + e.getMessage());
            }
            return 1;
        }
    }
}

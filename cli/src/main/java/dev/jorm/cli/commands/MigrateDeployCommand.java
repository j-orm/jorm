package dev.jorm.cli.commands;

import dev.jorm.cli.output.Printer;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "deploy", description = "Applies all pending migrations to the production database")
public class MigrateDeployCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        Printer.warn("Migrate deploy is not fully implemented yet.");
        // TODO: Implement production database connection and migration runner
        return 0;
    }
}

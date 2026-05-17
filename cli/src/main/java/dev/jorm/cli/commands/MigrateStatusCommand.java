package dev.jorm.cli.commands;

import dev.jorm.cli.output.Printer;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "status", description = "Displays the current status of migrations")
public class MigrateStatusCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        Printer.info("Checking migration status...");
        Printer.warn("Migrate status is not fully implemented yet.");
        // TODO: Implement status check logic
        return 0;
    }
}

package dev.jorm.cli.commands;

import dev.jorm.cli.output.Printer;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "reset", description = "Reverts all applied migrations, clearing the database (for development only)")
public class MigrateResetCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        Printer.warn("Migrate reset is not fully implemented yet.");
        // TODO: Implement database reset logic
        return 0;
    }
}

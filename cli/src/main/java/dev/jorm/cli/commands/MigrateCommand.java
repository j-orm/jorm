package dev.jorm.cli.commands;

import dev.jorm.cli.output.Printer;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "migrate", description = "Manage database migrations",
        subcommands = { 
            MigrateDevCommand.class,
            MigrateDeployCommand.class,
            MigrateStatusCommand.class,
            MigrateResetCommand.class
        })
public class MigrateCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        Printer.info("Use 'jorm migrate --help' to see available subcommands.");
        return 0;
    }
}

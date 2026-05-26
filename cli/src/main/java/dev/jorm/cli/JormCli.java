package dev.jorm.cli;

import dev.jorm.cli.commands.FormatCommand;
import dev.jorm.cli.commands.GenerateCommand;
import dev.jorm.cli.commands.InitCommand;
import dev.jorm.cli.commands.MigrateCommand;
import dev.jorm.cli.commands.ValidateCommand;
import dev.jorm.cli.output.Printer;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.util.concurrent.Callable;

@Command(name = "jorm", mixinStandardHelpOptions = true, version = "Jorm 0.1.3",
        description = "Jorm: A Schema-First Modern ORM for Java",
        subcommands = {
                InitCommand.class,
                GenerateCommand.class,
                MigrateCommand.class,
                ValidateCommand.class,
                FormatCommand.class
        })
public class JormCli implements Callable<Integer> {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new JormCli()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public Integer call() {
        Printer.info("Use 'jorm --help' to see available commands.");
        return 0;
    }
}

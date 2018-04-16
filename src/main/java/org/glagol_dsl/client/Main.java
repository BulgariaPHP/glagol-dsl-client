package org.glagol_dsl.client;

import org.glagol_dsl.client.shell.command.CleanCommand;
import org.glagol_dsl.client.shell.command.Command;
import org.glagol_dsl.client.shell.command.CompileCommand;
import org.glagol_dsl.client.shell.command.MainCommand;
import picocli.CommandLine;

import java.util.List;

public class Main {

    private Command lastCommand;

    private Main(String... args) {
        CommandLine commandLine = new CommandLine(new MainCommand());
        commandLine.addSubcommand("compile", new CompileCommand());
        commandLine.addSubcommand("clean", new CleanCommand());

        List<CommandLine> commandLines = commandLine.parse(args);

        if (commandLines.size() == 1 && args.length == 0) {
            commandLine.printVersionHelp(System.out);
            commandLine.usage(System.out);

            System.exit(0);
        }

        commandLines.forEach(this::handleCommand);
    }

    private void handleCommand(CommandLine commandLine) {
        if (commandLine.isUsageHelpRequested()) {
            commandLine.usage(System.out);

            System.exit(0);
        }

        if (commandLine.isVersionHelpRequested()) {
            commandLine.printVersionHelp(System.out);

            System.exit(0);
        }

        Command command = (Command) commandLine.getCommand();

        try {
            command.execute(lastCommand, new ConsoleStream(System.out));
        } catch (Throwable e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        lastCommand = command;
    }

    public static void main(String... args) {
        new Main(args);
    }
}

package org.bulgaria_php.glagol_dsl.client;

import org.bulgaria_php.glagol_dsl.client.shell.command.GlagolCommand;
import picocli.CommandLine;

import java.io.IOException;
import java.util.List;

public class Main {

    private GlagolCommand lastCommand;

    private Main(String... args) {
        CommandLine commandLine = new CommandLine(new org.bulgaria_php.glagol_dsl.client.shell.command.Main());
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

        GlagolCommand command = (GlagolCommand) commandLine.getCommand();

        try {
            command.execute(lastCommand);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        lastCommand = command;
    }

    public static void main(String... args) {
        new Main(args);
    }
}

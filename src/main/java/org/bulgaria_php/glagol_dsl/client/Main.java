package org.bulgaria_php.glagol_dsl.client;

import org.bulgaria_php.glagol_dsl.client.Shell.Command.Client;
import org.bulgaria_php.glagol_dsl.client.Shell.Command.GlagolCommand;
import picocli.CommandLine;

import java.util.List;

public class Main {

    private Main(String... args) {
        CommandLine commandLine = new CommandLine(new Client());
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

        ((GlagolCommand) commandLine.getCommand()).execute();
    }

    public static void main(String... args) {
        new Main(args);
    }
}

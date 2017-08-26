package org.bulgaria_php.glagol_dsl.client.shell.command;

import org.bulgaria_php.glagol_dsl.client.CompilePath;
import org.bulgaria_php.glagol_dsl.client.ConsoleStream;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;

@picocli.CommandLine.Command(name = "clean", description = "Clean compiled sources")
public class CleanCommand implements Command {

    @CommandLine.Option(names = {"-p", "--path"}, description = "Path to the project root directory")
    private File projectDir;

    @SuppressWarnings("unused")
    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "Display this help message")
    private boolean usageHelpRequested;

    @CommandLine.Option(names = {"-v", "--verbose"}, description = "Show verbose messages")
    private boolean verbose = false;

    @Override
    public void execute(MainCommand command, ConsoleStream consoleStream) throws IOException {
        new CompilePath(projectDir).cleanUpCompiledSources(consoleStream, verbose);
    }

    public void execute(Command previous, ConsoleStream consoleStream) throws IOException {
        previous.execute(this, consoleStream);
    }
}

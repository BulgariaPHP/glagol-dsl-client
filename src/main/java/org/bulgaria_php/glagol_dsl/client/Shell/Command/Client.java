package org.bulgaria_php.glagol_dsl.client.Shell.Command;

import org.bulgaria_php.glagol_dsl.client.Version;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(
    name = "Glagol DSL Client",
    subcommands = {Compile.class},
    version = {Version.VERSION}
)
public class Client implements GlagolCommand {
    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    private boolean usageHelpRequested;

    @Option(names = { "-v", "--version" }, versionHelp = true, description = "print version information and exit")
    private boolean versionRequested;
}

package org.bulgaria_php.glagol_dsl.client.Shell.Command;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;

@Command(name = "compile", description = "Compile project")
public class Compile implements GlagolCommand {

    @Option(names = {"-p", "--path"}, description = "Path to the project root directory")
    private File projectDir;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    private boolean usageHelpRequested;

    @Override
    public void execute() {
        if (null == projectDir) {
            System.out.println("No project dir assigned, will use cwd");
        }
    }
}

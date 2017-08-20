package org.bulgaria_php.glagol_dsl.client.shell.command;

import org.bulgaria_php.glagol_dsl.client.request.CompileRequest;
import org.bulgaria_php.glagol_dsl.client.socket.Client;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.IOException;

@Command(name = "compile", description = "Compile project")
public class Compile implements GlagolCommand {

    @Option(names = {"-p", "--path"}, description = "Path to the project root directory")
    private File projectDir;

    @SuppressWarnings("unused")
    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Display this help message")
    private boolean usageHelpRequested;

    /**
     * Executes compile command to the server
     *
     * @param command the client command
     */
    @Override
    public void execute(Main command) throws IOException {
        Client client = command.createClient();
        CompileRequest request = new CompileRequest(lookupProjectDir());

        client.makeRequest(request, response -> response.report(System.out, System.err));
    }

    private File lookupProjectDir() {
        return isEmpty(projectDir) ? new File(System.getProperty("user.dir")) : projectDir;
    }

    /**
     * This method is only used for double-dispatching
     *
     * @param previous Previously executed command
     */
    @Override
    public void execute(GlagolCommand previous) throws IOException {
        previous.execute(this);
    }

    private boolean isEmpty(File dir) {
        return dir == null || dir.length() == 0;
    }
}

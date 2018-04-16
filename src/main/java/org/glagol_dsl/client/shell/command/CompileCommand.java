package org.glagol_dsl.client.shell.command;

import org.glagol_dsl.client.CompilePath;
import org.glagol_dsl.client.ConsoleStream;
import org.glagol_dsl.client.request.CompileRequest;
import org.glagol_dsl.client.socket.Client;
import picocli.CommandLine.Option;

import java.io.File;
import java.io.IOException;

@picocli.CommandLine.Command(name = "compile", description = "Compile project")
public class CompileCommand implements Command {

    @Option(names = {"-p", "--path"}, description = "Path to the project root directory")
    private File projectDir;

    @SuppressWarnings("unused")
    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Display this help message")
    private boolean usageHelpRequested;

    @Option(names = {"-v", "--verbose"}, description = "Show verbose messages")
    private boolean verbose = false;

    /**
     * Executes compile command to the server
     *
     * @param command the client command
     */
    @Override
    public void execute(MainCommand command, ConsoleStream consoleStream) throws IOException {
        Client client = command.createClient();
        CompileRequest request = new CompileRequest(lookupProjectDir());
        CompilePath compilePath = new CompilePath(projectDir);

        client.makeRequest(request, response -> response.handleResponse(compilePath, consoleStream, verbose));
    }

    public void execute(Command previous, ConsoleStream consoleStream) throws IOException {
        previous.execute(this, consoleStream);
    }

    private File lookupProjectDir() {
        return isEmpty(projectDir) ? new File(System.getProperty("user.dir")) : projectDir;
    }

    private boolean isEmpty(File dir) {
        return dir == null || dir.length() == 0;
    }
}

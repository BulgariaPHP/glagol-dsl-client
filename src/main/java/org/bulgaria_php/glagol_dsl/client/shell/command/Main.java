package org.bulgaria_php.glagol_dsl.client.shell.command;

import org.bulgaria_php.glagol_dsl.client.Version;
import org.bulgaria_php.glagol_dsl.client.socket.Client;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;

@Command(
    name = "Glagol DSL Main",
    subcommands = {Compile.class},
    version = {Version.VERSION}
)
public class Main implements GlagolCommand {
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final Integer DEFAULT_PORT = 51151;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Display this help message")
    private boolean usageHelpRequested;

    @Option(names = {"-v", "--version"}, versionHelp = true, description = "Print version information and exit")
    private boolean versionRequested;

    @Option(names = {"-H", "--host"}, description = "Host on which the Glagol DSL Server is running")
    private String host;

    @Option(names = {"-p", "--port"}, description = "Host port on which the Glagol DSL Server is running")
    private Integer port;

    public Main() {
        host = lookupHost(DEFAULT_HOST);
        port = lookupPort(DEFAULT_PORT);
    }

    private String lookupHost(String defaultValue) {
        String value = System.getenv("GLAGOL_DSL_HOST");
        return isEmpty(value) ? defaultValue : value;
    }

    private Integer lookupPort(Integer defaultValue) {
        String value = System.getenv("GLAGOL_DSL_PORT");
        return isEmpty(value) ? defaultValue : Integer.valueOf(value);
    }

    Client createClient() {
        return new Client(host, port);
    }

    private boolean isEmpty(String host) {
        return host == null || host.length() == 0;
    }

    /**
     * This method is only used for double-dispatching
     *
     * @param command Previously executed command
     */
    @Override
    public void execute(Compile command) throws IOException {
        command.execute(this);
    }
}

package org.glagol_dsl.client.shell.command;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.agentproxy.AgentProxyException;
import com.jcraft.jsch.agentproxy.Connector;
import com.jcraft.jsch.agentproxy.ConnectorFactory;
import com.jcraft.jsch.agentproxy.RemoteIdentityRepository;
import org.glagol_dsl.client.ConsoleStream;
import org.glagol_dsl.client.Version;
import org.glagol_dsl.client.socket.Client;
import picocli.CommandLine.Option;

import java.io.IOException;

@picocli.CommandLine.Command(
    name = "glagol",
    version = {Version.VERSION}
)
public class MainCommand implements Command {

    private static final String DEFAULT_HOST = "35.204.215.192";
    private static final Integer DEFAULT_PORT = 51151;

    private static final String TUNNEL_REMOTE_HOST = "glagol-server-svc.default.svc.cluster.local";
    private static final int TUNNEL_REMOTE_PORT = 51151;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "Display this help message")
    private boolean usageHelpRequested;

    @Option(names = {"-v", "--version"}, versionHelp = true, description = "Print version information and exit")
    private boolean versionRequested;

    @Option(names = {"-H", "--host"}, description = "Host on which the Glagol DSL Server is running")
    private String host;

    @Option(names = {"-p", "--port"}, description = "Host port on which the Glagol DSL Server is running")
    private Integer port;

    @Option(names = {"-l", "--no-ssh-auth"}, description = "Suspends ssh authentication. Note: glagol.io requires ssh authentication")
    private boolean suspendAuth;

    private Session session;

    public MainCommand() {
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
        return new Client(suspendAuth ? lookupHost("localhost") : "localhost", port);
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
    public void execute(CompileCommand command, ConsoleStream consoleStream) throws IOException {
        openTunnel();
        command.execute(this, consoleStream);
        closeTunnel();
    }

    /**
     * This method is only used for double-dispatching
     *
     * @param command Previously executed command
     */
    @Override
    public void execute(CleanCommand command, ConsoleStream consoleStream) throws IOException {
        command.execute(this, consoleStream);
    }

    private void openTunnel() {
        if (suspendAuth) {
            System.out.println(
                    "Warning: ssh authentication is disabled. " +
                    "This can be potentially dangerous since the communication with the server will not be encrypted.");
            return;
        }

        JSch.setConfig("StrictHostKeyChecking", "no");

        JSch jSch = new JSch();

        try {
            ConnectorFactory cf = ConnectorFactory.getDefault();
            Connector con = cf.createConnector();
            jSch.setIdentityRepository(new RemoteIdentityRepository(con));
        } catch (AgentProxyException e) {
            e.printStackTrace(System.err);
            return;
        }

        try {
            session = jSch.getSession("glagol-client", host);
            session.connect();
            session.setPortForwardingL(port, TUNNEL_REMOTE_HOST, TUNNEL_REMOTE_PORT);
        } catch (JSchException e) {
            System.err.println("Error: unable to authenticate through SSH: " + e.getMessage());
            System.exit(255);
        }
    }

    private void closeTunnel() {
        if (session == null) {
            return;
        }

        try {
            session.delPortForwardingL(port);
            session.disconnect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }
}

package org.glagol_dsl.client.response;

import org.glagol_dsl.client.CompilePath;
import org.glagol_dsl.client.ConsoleStream;

public class CleanResponse implements Response {
    @Override
    public void handleResponse(CompilePath compilePath, ConsoleStream consoleStream, boolean showVerbose) {
        compilePath.cleanUpCompiledSources(consoleStream, showVerbose);
    }
}

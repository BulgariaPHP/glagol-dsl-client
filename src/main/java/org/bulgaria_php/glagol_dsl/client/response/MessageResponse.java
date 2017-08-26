package org.bulgaria_php.glagol_dsl.client.response;

import org.bulgaria_php.glagol_dsl.client.CompilePath;
import org.bulgaria_php.glagol_dsl.client.ConsoleStream;

public class MessageResponse implements Response {

    private final String message;

    MessageResponse(String message) {
        this.message = message;
    }

    @Override
    public void handleResponse(CompilePath projectDir, ConsoleStream consoleStream, boolean showVerbose) {
        consoleStream.println(message);
    }
}

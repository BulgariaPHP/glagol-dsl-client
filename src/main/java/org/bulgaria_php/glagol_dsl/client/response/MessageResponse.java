package org.bulgaria_php.glagol_dsl.client.response;

import org.bulgaria_php.glagol_dsl.client.CompilePath;

import java.io.PrintStream;

public class MessageResponse implements Response {

    private final String message;

    MessageResponse(String message) {

        this.message = message;
    }

    @Override
    public void handleResponse(PrintStream out, PrintStream err, CompilePath projectDir, boolean verbose) {
        out.println(message);
    }
}

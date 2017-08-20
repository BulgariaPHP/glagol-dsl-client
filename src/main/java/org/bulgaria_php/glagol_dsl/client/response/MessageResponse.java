package org.bulgaria_php.glagol_dsl.client.response;

import java.io.PrintStream;

public class MessageResponse implements Response {

    private final String message;

    MessageResponse(String message) {

        this.message = message;
    }

    @Override
    public void report(PrintStream out, PrintStream err) {
        out.println(message);
    }
}

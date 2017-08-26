package org.bulgaria_php.glagol_dsl.client;

import java.io.PrintStream;

public class ConsoleStream {
    private final PrintStream out;

    public ConsoleStream(PrintStream out) {
        this.out = out;
    }

    public void println(String message, boolean showVerbose) {
        if (showVerbose) {
            println(message);
        }
    }

    public void println(String message) {
        out.println(message);
    }
}

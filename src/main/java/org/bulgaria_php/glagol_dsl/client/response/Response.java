package org.bulgaria_php.glagol_dsl.client.response;

import org.bulgaria_php.glagol_dsl.client.CompilePath;

import java.io.PrintStream;

public interface Response {
    default boolean isEnd() {
        return false;
    }

    default void handleResponse(PrintStream out, PrintStream err, CompilePath compilePath, boolean verbose) {}

    default void log(PrintStream printStream, boolean verbose, String x) {
        if (verbose) {
            printStream.println(x);
        }
    }
}

package org.bulgaria_php.glagol_dsl.client.response;

import java.io.File;
import java.io.PrintStream;

public interface Response {
    default boolean isEnd() {
        return false;
    }

    default void handleResponse(PrintStream out, PrintStream err, File projectDir, boolean verbose) {}
}

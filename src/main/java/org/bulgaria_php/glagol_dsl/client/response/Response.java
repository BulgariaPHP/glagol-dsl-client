package org.bulgaria_php.glagol_dsl.client.response;

import java.io.PrintStream;

public interface Response {
    default boolean isEnd() {
        return false;
    }

    default void report(PrintStream out, PrintStream err) {}
}

package org.glagol_dsl.client.response;

import org.glagol_dsl.client.CompilePath;
import org.glagol_dsl.client.ConsoleStream;

public interface Response {
    default boolean isEnd() {
        return false;
    }

    default void handleResponse(CompilePath compilePath, ConsoleStream consoleStream, boolean showVerbose) {}
}

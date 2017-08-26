package org.bulgaria_php.glagol_dsl.client.response;

import org.bulgaria_php.glagol_dsl.client.CompilePath;
import org.bulgaria_php.glagol_dsl.client.ConsoleStream;

import java.io.File;
import java.io.IOException;

public class CreateFileResponse implements Response {
    private final File file;
    private final String contents;

    CreateFileResponse(File file, String contents) throws IOException {
        this.file = file;
        this.contents = contents;
    }

    @Override
    public void handleResponse(CompilePath compilePath, ConsoleStream consoleStream, boolean showVerbose) {
        compilePath.safeWriteFile(file, contents);
        consoleStream.println("+ " + file, showVerbose);
    }
}

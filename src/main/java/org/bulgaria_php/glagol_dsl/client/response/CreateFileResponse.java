package org.bulgaria_php.glagol_dsl.client.response;

import org.bulgaria_php.glagol_dsl.client.CompilePath;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class CreateFileResponse implements Response {
    private final File file;
    private final String contents;

    CreateFileResponse(File file, String contents) throws IOException {
        this.file = file;
        this.contents = contents;
    }

    @Override
    public void handleResponse(PrintStream out, PrintStream err, CompilePath compilePath, boolean verbose) {
        compilePath.safeWriteFile(file, contents);
        log(out, verbose, "+ " + file);
    }
}

package org.bulgaria_php.glagol_dsl.client.response;

import org.bulgaria_php.glagol_dsl.client.CompilePath;

import java.io.File;
import java.io.PrintStream;
import java.util.List;

public class CreateLogFileResponse implements Response {
    static final String GLAGOL_COMPILE_LOG = ".glagol_compile_log";

    private final List<File> logFileContents;
    private final File file;

    CreateLogFileResponse(List<File> logFileContents) {
        this.logFileContents = logFileContents;
        file = new File(GLAGOL_COMPILE_LOG);
    }

    @Override
    public void handleResponse(PrintStream out, PrintStream err, CompilePath compilePath, boolean verbose) {
        compilePath.safeWriteObjectFile(file, logFileContents);
        log(out, verbose, "+ " + file);
    }
}

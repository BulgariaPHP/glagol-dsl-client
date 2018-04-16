package org.glagol_dsl.client.response;

import org.glagol_dsl.client.CompilePath;
import org.glagol_dsl.client.ConsoleStream;
import org.glagol_dsl.client.io.CompileLogFile;

import java.io.File;
import java.util.List;

public class CreateLogFileResponse implements Response {
    private final List<File> logFileContents;
    private final File file = new CompileLogFile();

    CreateLogFileResponse(List<File> logFileContents) {
        this.logFileContents = logFileContents;
    }

    @Override
    public void handleResponse(CompilePath compilePath, ConsoleStream consoleStream, boolean showVerbose) {
        compilePath.safeWriteObjectFile(file, logFileContents);
        consoleStream.println("+ " + file, showVerbose);
    }
}

package org.bulgaria_php.glagol_dsl.client.response;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CreateLogFileResponse implements Response {
    private final File file;
    private final List<File> logFileContents;

    CreateLogFileResponse(File file, List<File> logFileContents) {
        this.file = file;
        this.logFileContents = logFileContents;
    }

    @Override
    public void handleResponse(PrintStream out, PrintStream err) {
        try {
            tryToCreateFile();
            out.println("Created log file " + file.toPath().toRealPath());
        } catch (IOException e) {
            err.println("Could not create file " + file.getAbsolutePath());
        }
    }

    private void tryToCreateFile() throws IOException {
        Path realPath = file.toPath();

        Files.createDirectories(realPath.getParent());
        Files.createFile(realPath);

        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(realPath.toFile()));
        output.writeObject(logFileContents);
    }
}

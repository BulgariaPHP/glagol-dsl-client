package org.bulgaria_php.glagol_dsl.client.response;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class CreateFileResponse implements Response {
    private final File file;
    private final String contents;

    CreateFileResponse(File file, String contents) throws IOException {
        this.file = file;
        this.contents = contents;
    }

    @Override
    public void handleResponse(PrintStream out, PrintStream err, File projectDir, boolean verbose) {
        try {
            tryToCreateFile();
            message(out, verbose, "+ " + file.toPath().toRealPath());
        } catch (IOException e) {
            message(err, verbose, "Could not create file " + file.getAbsolutePath() + ": " + e.getMessage());
        }
    }

    private void message(PrintStream printStream, boolean verbose, String x) {
        if (verbose) {
            printStream.println(x);
        }
    }

    private void tryToCreateFile() throws IOException {
        Path realPath = file.toPath();

        Files.createDirectories(realPath.getParent());
        Files.createFile(realPath);

        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        output.write(contents);
        output.flush();
    }
}

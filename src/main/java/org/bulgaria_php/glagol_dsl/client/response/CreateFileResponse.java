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
    public void handleResponse(PrintStream out, PrintStream err) {
        try {
            tryToCreateFile();
            out.println("Created file " + file.toPath().toRealPath());
        } catch (IOException e) {
            err.println("Could not create file " + file.getAbsolutePath() + ": " + e.getMessage());
        }
    }

    private void tryToCreateFile() throws IOException {
        Path realPath = file.toPath();

        Files.createDirectories(realPath.getParent());
        Files.createFile(realPath);

        BufferedWriter output = new BufferedWriter(new FileWriter(realPath.toRealPath().toFile()));
        output.write(contents);
    }
}

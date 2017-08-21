package org.bulgaria_php.glagol_dsl.client;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class CompilePath {

    private final File compilePath;

    public CompilePath(File projectPath) {
        this(projectPath, "out");
    }

    public CompilePath(File projectPath, String outDir) {
        this.compilePath = new File(projectPath, outDir);
    }

    @NotNull
    public File file(File file) {
        return new File(compilePath, file.getPath());
    }

    public void safeDelete(File file) {
        try {
            Files.delete(file(file).toPath());
        } catch (IOException ignored) {
        }
    }

    public void safeWriteObjectFile(File file, Object contents) {
        try {
            writeObjectFile(file, contents);
        } catch (IOException ignored) {
        }
    }

    public void safeWriteFile(File file, String contents) {
        try {
            writeFile(file, contents);
        } catch (IOException ignored) {
        }
    }

    private void writeFile(File file, String contents) throws IOException {
        Path filePath = newFile(file);

        BufferedWriter output = new BufferedWriter(new FileWriter(filePath.toFile()));
        output.write(contents);
        output.flush();
    }

    private void writeObjectFile(File file, Object contents) throws IOException {
        Path filePath = newFile(file);

        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filePath.toFile()));
        output.writeObject(contents);
        output.flush();
    }

    @NotNull
    private Path newFile(File file) throws IOException {
        Path filePath = file(file).toPath();

        Files.createDirectories(filePath.getParent());
        Files.createFile(filePath);

        return filePath;
    }
}

package org.bulgaria_php.glagol_dsl.client;

import org.bulgaria_php.glagol_dsl.client.io.CompileLogFile;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CompilePath {

    private final File compilePath;

    public CompilePath(File projectPath) {
        this(projectPath, "out");
    }

    private CompilePath(File projectPath, String outDir) {
        this.compilePath = new File(projectPath, outDir);
    }

    @NotNull
    public File file(File file) {
        return new File(compilePath, file.getPath());
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

    public void cleanUpCompiledSources(ConsoleStream consoleStream, boolean showVerbose) {
        File relativeLogFile = new CompileLogFile();

        File logFile = file(relativeLogFile);

        if (logFile.exists()) {
            List<File> files = getCompiledFilesList(logFile);
            files.forEach(deleteFileClosure(consoleStream, showVerbose));
        }

        safeDelete(relativeLogFile);
        consoleStream.println("- " + relativeLogFile, showVerbose);
    }

    private void safeDelete(File file) {
        try {
            Files.delete(file(file).toPath());
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

    @NotNull
    private Consumer<File> deleteFileClosure(ConsoleStream consoleStream, boolean showVerbose) {
        return file -> {
            safeDelete(file);
            consoleStream.println("- " + file, showVerbose);
        };
    }

    private List<File> getCompiledFilesList(File logFile) {
        try {
            return readCompilationLog(logFile);
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>();
        }
    }

    private List<File> readCompilationLog(File logFile) throws IOException, ClassNotFoundException {
        FileInputStream fileIn;
        fileIn = new FileInputStream(logFile);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        //noinspection unchecked
        List<File> files = (List<File>) in.readObject();
        in.close();
        fileIn.close();
        return files;
    }
}

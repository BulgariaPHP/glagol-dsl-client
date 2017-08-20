package org.bulgaria_php.glagol_dsl.client.response;

import java.io.*;
import java.nio.file.NoSuchFileException;
import java.util.List;

public class CleanResponse implements Response {

    private final File logFile;

    CleanResponse(File logFile) {
        this.logFile = logFile;
    }

    @Override
    public void handleResponse(PrintStream out, PrintStream err, File projectDir, boolean verbose) {
        try {
            tryToCleanUpOldFiles(out, err, verbose);
        } catch (IOException | ClassNotFoundException e) {
            err.println(e.getMessage());
        }
    }

    private void tryToCleanUpOldFiles(PrintStream out, PrintStream err, boolean verbose) throws IOException, ClassNotFoundException {
        if (logFile.exists()) {
            FileInputStream fileIn = new FileInputStream(logFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            //noinspection unchecked
            for (File file : ((List<File>) in.readObject())) {
                try {
                    File realFile = file.toPath().toRealPath().toFile();
                    realFile.delete();
                    message(out, verbose, "- " + realFile);
                } catch (FileNotFoundException | NoSuchFileException e) {
                    message(err, verbose, "Cannot locate " + file);
                }
            }
            in.close();
            fileIn.close();

            logFile.delete();
            message(out, verbose, "- " + logFile);
        }
    }

    private void message(PrintStream printStream, boolean verbose, String x) {
        if (verbose) {
            printStream.println(x);
        }
    }
}

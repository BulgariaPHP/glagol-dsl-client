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
    public void handleResponse(PrintStream out, PrintStream err) {
        try {
            tryToCleanUpOldFiles(out, err);
        } catch (IOException | ClassNotFoundException e) {
            err.println(e.getMessage());
        }
    }

    private void tryToCleanUpOldFiles(PrintStream out, PrintStream err) throws IOException, ClassNotFoundException {
        if (logFile.exists()) {
            FileInputStream fileIn = new FileInputStream(logFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            //noinspection unchecked
            for (File file : ((List<File>) in.readObject())) {
                try {
                    File realFile = file.toPath().toRealPath().toFile();
                    realFile.delete();
                    out.println("Deleted source file " + realFile);
                } catch (FileNotFoundException | NoSuchFileException e) {
                    System.err.println("Cannot locate file " + file);
                }
            }
            in.close();
            fileIn.close();

            logFile.delete();
        }
    }
}

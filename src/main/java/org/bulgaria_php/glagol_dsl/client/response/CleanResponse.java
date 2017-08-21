package org.bulgaria_php.glagol_dsl.client.response;

import org.bulgaria_php.glagol_dsl.client.CompilePath;

import java.io.*;
import java.util.List;

public class CleanResponse implements Response {

    @Override
    public void handleResponse(PrintStream out, PrintStream err, CompilePath compilePath, boolean verbose) {
        try {
            tryToCleanUpOldFiles(out, err, verbose, compilePath);
        } catch (IOException | ClassNotFoundException e) {
            err.println(e.getMessage());
        }
    }

    private void tryToCleanUpOldFiles(PrintStream out, PrintStream err, boolean verbose, CompilePath compilePath)
            throws IOException, ClassNotFoundException {

        File relativeLogFile = new File(CreateLogFileResponse.GLAGOL_COMPILE_LOG);

        File logFile = compilePath.file(relativeLogFile);

        if (logFile.exists()) {
            FileInputStream fileIn = new FileInputStream(logFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            //noinspection unchecked
            List<File> files = (List<File>) in.readObject();
            files.forEach(file -> {
                compilePath.safeDelete(file);
                log(out, verbose, "- " + file);
            });
            in.close();
            fileIn.close();
        }

        compilePath.safeDelete(relativeLogFile);
        log(out, verbose, "- " + relativeLogFile);
    }
}

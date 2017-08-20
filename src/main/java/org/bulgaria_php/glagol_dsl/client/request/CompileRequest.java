package org.bulgaria_php.glagol_dsl.client.request;

import org.apache.commons.io.FilenameUtils;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Base64.getEncoder;

public class CompileRequest implements Request {
    private final File projectDir;

    public CompileRequest(File projectDir) {
        this.projectDir = projectDir;
    }

    @Override
    public void sendTo(PrintWriter printWriter) {
        printWriter.println(getEncodedMessage());
    }

    private String getEncodedMessage() {
        String jsonString = getJson().toString();

        return getEncoder().encodeToString(jsonString.getBytes());
    }

    private JsonObject getJson() {
        return Json.createObjectBuilder()
                .add("command", "compile")
                .add("path", projectDir.toURI().getRawPath())
                .add("files", lookupExistingFiles())
                .build();
    }

    private JsonArray lookupExistingFiles() {
        JsonArrayBuilder builder = Json.createArrayBuilder();

        scanProjectDir().forEach(file -> builder.add(file.getAbsolutePath()));

        return builder.build();
    }

    private List<File> scanProjectDir() {
        List<File> files = new ArrayList<>();
        scanForGlagolFiles(projectDir, files);

        return files;
    }

    private void scanForGlagolFiles(File dir, List<File> files) {
        File[] children = dir.listFiles();
        if (children == null) {
            return;
        }

        Arrays.stream(children).forEach(child -> {
            if (FilenameUtils.isExtension(child.getName(), "g")) {
                files.add(child);
            }

            if (child.isDirectory()) {
                scanForGlagolFiles(child, files);
            }
        });
    }
}

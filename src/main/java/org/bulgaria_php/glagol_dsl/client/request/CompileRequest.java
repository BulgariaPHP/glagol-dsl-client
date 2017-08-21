package org.bulgaria_php.glagol_dsl.client.request;

import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
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
                .add("sources", collectSources())
                .build();
    }

    private JsonObject collectSources() {
        JsonObjectBuilder builder = Json.createObjectBuilder();

        scanProjectDir().forEach(file -> {
            if (file.exists()) {
                try {
                    builder.add(toRelativePath(file), new String(Files.readAllBytes(file.toPath())));
                } catch (IOException ignored) {}
            }
        });

        return builder.build();
    }

    @NotNull
    private String toRelativePath(File file) {
        return file.getAbsolutePath().replace(projectDir.getAbsolutePath(), "");
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

package org.bulgaria_php.glagol_dsl.client.request;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.File;
import java.io.PrintWriter;

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
                .build();
    }
}

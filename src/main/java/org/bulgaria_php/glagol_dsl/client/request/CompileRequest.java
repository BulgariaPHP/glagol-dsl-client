package org.bulgaria_php.glagol_dsl.client.request;

import javax.json.Json;
import java.io.File;
import java.io.PrintWriter;

public class CompileRequest implements Request {
    private final File projectDir;

    public CompileRequest(File projectDir) {
        this.projectDir = projectDir;
    }

    @Override
    public void sendTo(PrintWriter printWriter) {
        printWriter.println(getJson());
    }

    private String getJson() {
        return Json.createObjectBuilder()
                .add("command", "compile")
                .add("path", projectDir.toURI().getRawPath())
                .build()
                .toString();
    }
}

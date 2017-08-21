package org.bulgaria_php.glagol_dsl.client.response;

import javax.json.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResponseFactory {

    public Response create(JsonStructure jsonStructure) throws IOException {

        JsonPointer typePointer = Json.createPointer("/type");
        JsonString typeNode = (JsonString) typePointer.getValue(jsonStructure);

        switch (typeNode.getString()) {
            case "clean":
                return new CleanResponse();
            case "create":
                return new CreateFileResponse(getFile(jsonStructure), getContents(jsonStructure));
            case "create_log":
                return new CreateLogFileResponse(getLogFileContents(jsonStructure));
            case "end":
                return new EndResponse();
        }

        JsonString contentNode = getMessage(jsonStructure);

        return new MessageResponse(contentNode.getString());
    }

    private List<File> getLogFileContents(JsonStructure jsonStructure) {
        JsonArray files = (JsonArray) jsonStructure.getValue("/files");
        List<File> sources = new ArrayList<>();

        files.forEach(file -> sources.add(new File(((JsonString) file).getString())));

        return sources;
    }

    private String getContents(JsonStructure jsonStructure) {
        return ((JsonString) jsonStructure.getValue("/contents")).getString();
    }

    private File getFile(JsonStructure jsonStructure) {
        return new File(((JsonString) jsonStructure.getValue("/file")).getString());
    }

    private JsonString getMessage(JsonStructure jsonStructure) {
        return (JsonString) jsonStructure.getValue("/message");
    }
}

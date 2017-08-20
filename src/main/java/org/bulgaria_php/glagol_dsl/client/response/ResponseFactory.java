package org.bulgaria_php.glagol_dsl.client.response;

import javax.json.*;

public class ResponseFactory {

    public Response create(JsonStructure jsonStructure) {

        JsonPointer typePointer = Json.createPointer("/type");
        JsonString typeNode = (JsonString) typePointer.getValue(jsonStructure);

        switch (typeNode.getString()) {
            case "end":
                return new EndResponse();
        }

        JsonString contentNode = (JsonString) jsonStructure.getValue("/args/0");

        return new MessageResponse(contentNode.getString());
    }
}

package org.glagol_dsl.client.response.handler;

import org.glagol_dsl.client.response.Response;

public interface Handler {
    void handle(Response response);
}

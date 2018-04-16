package org.glagol_dsl.client.response;

public class EndResponse implements Response {
    @Override
    public boolean isEnd() {
        return true;
    }
}

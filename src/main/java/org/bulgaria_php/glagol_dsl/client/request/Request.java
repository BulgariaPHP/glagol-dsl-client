package org.bulgaria_php.glagol_dsl.client.request;

import java.io.PrintWriter;

public interface Request {

    void sendTo(PrintWriter printWriter);
}

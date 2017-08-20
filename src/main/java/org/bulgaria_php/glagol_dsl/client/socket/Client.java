package org.bulgaria_php.glagol_dsl.client.socket;

import org.bulgaria_php.glagol_dsl.client.request.Request;
import org.bulgaria_php.glagol_dsl.client.response.Response;
import org.bulgaria_php.glagol_dsl.client.response.ResponseFactory;
import org.bulgaria_php.glagol_dsl.client.response.handler.Handler;
import org.jetbrains.annotations.NotNull;

import javax.json.Json;
import javax.json.JsonStructure;
import java.io.*;
import java.net.Socket;

import static java.util.Base64.getDecoder;

public class Client {
    private final String host;
    private final Integer port;

    private final ResponseFactory responseFactory = new ResponseFactory();

    public Client(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public void makeRequest(Request request, Handler responseHandler) throws IOException {
        Socket socket = createSocket();

        request.sendTo(new PrintWriter(socket.getOutputStream(), true));

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        for (;;) {
            String line = new String(getDecoder().decode(in.readLine().getBytes()));
            JsonStructure rawResponse = Json.createReader(new StringReader(line)).read();
            Response response = responseFactory.create(rawResponse);

            if (response.isEnd()) {
                break;
            }

            responseHandler.handle(response);
        }

        socket.close();
    }

    @NotNull
    private Socket createSocket() throws IOException {
        return new Socket(host, port);
    }
}

package http.server.handler;

import http.server.core.HttpRequest;

import java.io.OutputStream;

public interface RequestHandler {
    void handle(HttpRequest request, OutputStream outputStream) throws Exception;
}

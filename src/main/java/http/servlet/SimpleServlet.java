package http.servlet;

import http.server.core.HttpRequest;
import http.server.core.HttpResponse;

import java.io.IOException;

public interface SimpleServlet {
    void service(HttpRequest request, HttpResponse response) throws IOException;
}

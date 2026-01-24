package http.server;

import http.server.core.HttpMethod;
import http.server.core.HttpRequestParser;
import http.server.core.HttpRequest;
import http.server.core.HttpStatus;
import http.server.handler.ErrorResponseHandler;
import http.server.handler.RequestHandler;
import http.server.routing.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class RequestProcessor implements Runnable {
    private final static Logger logger = LoggerFactory.getLogger(RequestProcessor.class);
    private final Socket connection;
    private final ErrorResponseHandler errorResponseHandler;
    private final Router router;

    public RequestProcessor(Socket connection, ErrorResponseHandler errorResponseHandler, Router router) {
        this.connection = connection;
        this.errorResponseHandler = errorResponseHandler;
        this.router = router;
    }

    @Override
    public void run() {
        try (OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream())) {
            HttpRequest request = HttpRequestParser.parse(connection.getInputStream());
            logger.info("Request : {} {}", request.getHttpMethod(), request.getRequestURI());

            if (request.getHttpMethod() == null || !HttpMethod.GET.equals(request.getHttpMethod())) {
                errorResponseHandler.handle(outputStream, HttpStatus.NOT_IMPLEMENTED);
                return;
            }

            RequestHandler handler = router.route(request);
            handler.handle(request, outputStream);

        } catch (Exception ex) {
            logger.warn("Error talking to {}", connection.getRemoteSocketAddress(), ex);
            handleServerError();
        } finally {
            closeConection();
        }
    }

    private void handleServerError() {
        try (OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream())) {
            errorResponseHandler.handle(outputStream, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            logger.warn("Failed to send 500 error", e);
        }
    }

    private void closeConection() {
        try {
            connection.close();
        } catch (IOException ex) {
        }
    }

}

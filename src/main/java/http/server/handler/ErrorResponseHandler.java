package http.server.handler;

import http.config.ServerConfig;
import http.server.core.ContentType;
import http.server.core.HttpResponseWriter;
import http.server.core.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class ErrorResponseHandler {
    private final ServerConfig serverConfig;
    private final HttpResponseWriter responseWriter;

    public ErrorResponseHandler(ServerConfig serverConfig, HttpResponseWriter responseWriter) {
        this.serverConfig = serverConfig;
        this.responseWriter = responseWriter;
    }

    public void handle(OutputStream outputStream, HttpStatus status) throws IOException {
        String errorPagePath = serverConfig.getErrorPage(status.getCode());
        if(errorPagePath != null) {
            File errorFile = new File(serverConfig.getRootDirectory(), errorPagePath);
            if(errorFile.exists()) {
                byte[] errorData = Files.readAllBytes(errorFile.toPath());
                responseWriter.write(outputStream, status, ContentType.TEXT_HTML, errorData);
                return;
            }

            // 파일 없으면 기본 에러 응답
            responseWriter.writeDefaultError(outputStream, status);
        }
    }
}

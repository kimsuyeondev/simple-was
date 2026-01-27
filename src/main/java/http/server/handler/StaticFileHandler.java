package http.server.handler;

import http.config.ServerConfig;
import http.server.core.HttpRequest;
import http.server.core.HttpResponseWriter;
import http.server.core.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.file.Files;

public class StaticFileHandler implements RequestHandler {
    private final static Logger logger = LoggerFactory.getLogger(StaticFileHandler.class);

    private static final String DEFAULT_INDEX_FILE = "index.html";
    private final ServerConfig serverConfig;
    private final ErrorResponseHandler errorHandler;
    private final HttpResponseWriter responseWriter;

    public StaticFileHandler(ServerConfig serverConfig, HttpResponseWriter responseWriter, ErrorResponseHandler errorHandler) {
        this.serverConfig = serverConfig;
        this.responseWriter = responseWriter;
        this.errorHandler = errorHandler;
    }

    @Override
    public void handle(HttpRequest request, OutputStream outputStream) throws IOException {
        File rootDirectory = new File(serverConfig.getRootDirectory());
        String root = rootDirectory.getCanonicalPath();

        logger.info("Resolved Root: {}", root);

        String fileName = request.getRequestURI();

        if (fileName.endsWith("/")) fileName += DEFAULT_INDEX_FILE;

        File file = new File(rootDirectory, fileName.substring(1));

        if (!file.getCanonicalPath().startsWith(root)) {
            errorHandler.handle(outputStream, HttpStatus.FORBIDDEN);
            return;
        }

        if (!file.exists()) {
            errorHandler.handle(outputStream, HttpStatus.NOT_FOUND);
            return;
        }

        if (!file.canRead()) {
            errorHandler.handle(outputStream, HttpStatus.FORBIDDEN);
            return;
        }

        String contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName);
        byte[] data = Files.readAllBytes(file.toPath());

        responseWriter.write(outputStream, HttpStatus.OK, contentType, data);
    }

}

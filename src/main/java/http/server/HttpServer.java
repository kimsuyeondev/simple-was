package http.server;

import http.server.handler.ErrorResponseHandler;
import http.server.routing.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);
    private static final int DEFAULT_NUM_THREADS = 50;
    private static final int DEFAULT_PORT = 8000;

    private final ErrorResponseHandler errorHandler;
    private final Router router;

    public HttpServer(ErrorResponseHandler errorHandler,
                      Router router) {
        this.errorHandler = errorHandler;
        this.router = router;
    }

    public void start() throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(DEFAULT_NUM_THREADS);

        try (ServerSocket server = new ServerSocket(DEFAULT_PORT)) {
            while (true) {
                try {
                    Socket socket = server.accept();
                    // 매 요청마다 생성
                    Runnable r = new RequestProcessor(socket, errorHandler, router);
                    pool.submit(r);
                } catch (IOException ex) {
                    logger.warn("start() - Error accepting connection : {}", ex);
                }
            }
        }
    }
}

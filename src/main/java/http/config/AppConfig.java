package http.config;

import http.server.core.HttpResponseWriter;
import http.server.handler.ErrorResponseHandler;
import http.server.handler.ServletHandler;
import http.server.handler.StaticFileHandler;
import http.server.routing.Router;
import http.server.HttpServer;
/**
* Main.class 길어져서 분리했음
public class Main {
      public static void main(String[] args) {
          ServerConfig config = ConfigReader.read();

          HttpResponseWriter writer = new HttpResponseWriter();
          ErrorResponseHandler errorHandler = new ErrorResponseHandler(config, writer);
          StaticFileHandler staticHandler = new StaticFileHandler(config, writer, errorHandler);
          ServletHandler servletHandler = new ServletHandler(config, writer);
          Router router = new Router(staticHandler, servletHandler);

          HttpServer server = new HttpServer(config, errorHandler, router);
          server.start();
      }
  }
*/
public class AppConfig {
    private final ServerConfig serverConfig;
    private final HttpResponseWriter responseWriter;
    private final ErrorResponseHandler errorHandler;
    private final StaticFileHandler staticFileHandler;
    private final ServletHandler servletHandler;
    private final Router router;

    public AppConfig() {
        this.serverConfig = ConfigReader.read();
        this.responseWriter = new HttpResponseWriter();
        this.errorHandler = new ErrorResponseHandler(serverConfig, responseWriter);
        this.staticFileHandler = new StaticFileHandler(serverConfig, responseWriter, errorHandler);
        this.servletHandler = new ServletHandler(responseWriter);
        this.router = new Router(staticFileHandler, servletHandler);
    }

    public HttpServer httpServer() {
        return new HttpServer(errorHandler, router);
    }
}


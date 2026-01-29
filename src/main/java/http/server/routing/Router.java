package http.server.routing;

import http.server.core.HttpRequest;
import http.server.handler.RequestHandler;
import http.server.handler.ServletHandler;
import http.server.handler.StaticFileHandler;

public class Router {
    private final StaticFileHandler staticFileHandler;
    private final ServletHandler servletHandler;

    public Router(StaticFileHandler staticFileHandler, ServletHandler servletHandler) {
        this.staticFileHandler = staticFileHandler;
        this.servletHandler = servletHandler;
    }

    public RequestHandler route(HttpRequest request) {
        String uri = request.getRequestURI();

        String path = uri.startsWith("/") ? uri.substring(1) : uri;
        boolean isServlet = !path.isEmpty() && Character.isUpperCase(path.charAt(0));

        if (isServlet) {
            // 서블릿 처리
            return servletHandler;
        } else {
            // 정적 파일 처리
            return staticFileHandler;
        }
    }

}

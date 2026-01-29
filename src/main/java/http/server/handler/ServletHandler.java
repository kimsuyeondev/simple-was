package http.server.handler;

import http.server.core.HttpRequest;
import http.server.core.HttpResponse;
import http.server.core.HttpResponseWriter;
import http.server.core.HttpStatus;
import http.servlet.ServletMapper;
import http.servlet.SimpleServlet;

import java.io.OutputStream;

public class ServletHandler implements RequestHandler {
    private final HttpResponseWriter responseWriter;
    private final ServletMapper servletMapper;
    private final ErrorResponseHandler errorResponseHandler;

    public ServletHandler(HttpResponseWriter responseWriter, ErrorResponseHandler errorResponseHandler) {
        this.responseWriter = responseWriter;
        this.errorResponseHandler = errorResponseHandler;
        this.servletMapper = new ServletMapper();
    }

    @Override
    public void handle(HttpRequest request, OutputStream outputStream) throws Exception {
        try {
            HttpResponse response = new HttpResponse();

            //서블릿 찾기
            SimpleServlet servlet = servletMapper.getServlet(request.getRequestURI());

            // 서블릿 실행
            servlet.service(request, response);

            // 응답 전송
            String body = response.getBody();
            responseWriter.write(outputStream, HttpStatus.OK, response.getContentType(), body);
        } catch (ClassNotFoundException e) {
            errorResponseHandler.handle(outputStream, HttpStatus.NOT_FOUND);
        }
    }
}

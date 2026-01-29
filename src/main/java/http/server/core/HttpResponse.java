package http.server.core;

import java.io.StringWriter;
import java.io.Writer;

public class HttpResponse {
    private final StringWriter buffer = new StringWriter();
    private final String contentType;
    private final int statusCode;

    public HttpResponse() {
        this(200, ContentType.TEXT_HTML);
    }

    public HttpResponse(int statusCode, String contentType) {
        this.statusCode = statusCode;
        this.contentType = contentType;
    }

    public Writer getWriter() {
        return buffer;
    }

    public String getBody() {
        return buffer.toString();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getContentType() {
        return contentType;
    }


}

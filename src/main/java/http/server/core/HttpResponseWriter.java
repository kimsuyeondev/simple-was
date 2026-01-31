package http.server.core;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class HttpResponseWriter {
    private static final String HTTP_VERSION = "HTTP/1.1";
    private static final String SERVER_NAME = "JHTTP/2.0";
    private static final String CRLF = "\r\n";


    public void write(OutputStream outputStream, HttpStatus status, String contentType, byte[] body) throws IOException {
        StringBuilder header = new StringBuilder();
        header.append(HTTP_VERSION).append(" ").append(status.getCode()).append(" ").append(status.getMessage()).append(CRLF);
        header.append("Date: ").append(new Date()).append(CRLF);
        header.append("Server: ").append(SERVER_NAME).append(CRLF);
        header.append("Content-Length: ").append(body.length).append(CRLF);
        header.append("Content-Type: ").append(contentType).append(CRLF);
        header.append(CRLF);

        outputStream.write(header.toString().getBytes(StandardCharsets.UTF_8));
        outputStream.write(body);
        outputStream.flush();
    }

    public void writeDefaultError(OutputStream outputStream, HttpStatus status) throws IOException {
        String defaultBody = "<html><body><h1>" + status.getCode() + " " + status.getMessage() + "</h1></body></html>";
        byte[] bodyBytes = defaultBody.getBytes(StandardCharsets.UTF_8);
        write(outputStream, status, ContentType.TEXT_HTML, bodyBytes);
    }

    public void write(OutputStream outputStream, HttpStatus status, String contentType, String body) throws IOException {
        byte[] bodyBytes = body.getBytes(StandardCharsets.UTF_8);
        write(outputStream, status, contentType, bodyBytes);
    }

}

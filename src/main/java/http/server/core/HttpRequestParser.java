package http.server.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequestParser {

    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_HTTP_VERSION = "HTTP/1.0";
    private final static Logger logger = LoggerFactory.getLogger(HttpRequestParser.class);

    public static HttpRequest parse(InputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader((inputStream)));

        String requestLine = in.readLine();

        if (requestLine == null || requestLine.isEmpty()) {
            throw new IOException("Empty or null request line");
        }
        logger.info("Read requestLine: [{}]", requestLine);

        String[] tokens = requestLine.split("\\s+");
        String method = tokens[0];
        String requestURI = tokens[1];
        String httpVersion = tokens.length > 2 ? tokens[2] : DEFAULT_HTTP_VERSION;

        String host = parseHost(in);
        return new HttpRequest(method, requestURI, host);

    }

    private static String parseHost(BufferedReader in) throws IOException {
        String headerLine;
        String host = "";

        while ((headerLine = in.readLine()) != null && !headerLine.isEmpty()) {

            if(headerLine.startsWith("Host:")) {
                host = headerLine.substring(5).trim();
            }
        }

        if(host.isEmpty()) {
            host = DEFAULT_HOST;
        }
        if(host.contains(":")) {
            host = host.split(":")[0];
        }

        logger.info("Resolved Host: {}", host);
        return host;
    }

}

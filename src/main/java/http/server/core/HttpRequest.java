package http.server.core;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String requestURI;
    private final String host;
    private final HttpMethod httpMethod;

    private Map<String, String> parameters = new HashMap<>();

    public HttpRequest(String method, String requestURI, String host) {
        this.requestURI = splitURI(requestURI);
        this.host = host;
        this.httpMethod = HttpMethod.from(method);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    private String splitURI(String requestURI) {
        int idx = requestURI.indexOf("?");
        if(idx > -1) {
            String queryString = requestURI.substring(idx + 1);
            getQueryString(queryString);
            return requestURI.substring(0, idx);
        }else {
            return requestURI;
        }
    }

    private void getQueryString(String queryString) {
        String[] parts = queryString.split("&");
        for (String part : parts) {
            int idx = part.indexOf("=");
            if (idx > 0) {
                String key = part.substring(0, idx);
                String value = part.substring(idx + 1);
                parameters.put(key, value);
            }
        }
    }


    public String getParameter(String name) {
        return parameters.get(name);
    }

    public String getRequestURI() {
        return requestURI;
    }

    public String getHost() {
        return host;
    }
}

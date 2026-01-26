package http.server.core;

public enum HttpMethod {
    GET;

    public static HttpMethod from(String method) {
        try {
            return HttpMethod.valueOf(method);
        } catch (IllegalArgumentException e) {
            return null;  // 예외 대신 null
        }
    }
}

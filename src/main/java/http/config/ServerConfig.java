package http.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ServerConfig {
    private final String rootDirectory;
    private final Map<String, String> errorPages;

    @JsonCreator
    public ServerConfig(
            @JsonProperty("rootDirectory") String rootDirectory,
            @JsonProperty("errorPages") Map<String, String> errorPages) {
        this.rootDirectory = rootDirectory;
        this.errorPages = errorPages;
    }


    public String getRootDirectory() {
        return rootDirectory;
    }

    public String getErrorPage(int statusCode) {
        return errorPages.get(String.valueOf(statusCode));
    }

}

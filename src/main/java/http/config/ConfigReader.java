package http.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.InputStream;

public class ConfigReader {

    private static final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

    public static ServerConfig read() {
        return read("config.yml");
    }

    public static ServerConfig read(String configPath) {
        InputStream inputStream = ConfigReader.class
                .getClassLoader()
                .getResourceAsStream(configPath);

        if (inputStream == null) {
            throw new RuntimeException("Config file not found: " + configPath);
        }

        try {
            return mapper.readValue(inputStream, ServerConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse config", e);
        }
    }
}

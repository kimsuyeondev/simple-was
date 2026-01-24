package http.server;

import http.config.AppConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            //ServerConfig config = ConfigReader.read();
            logger.info("Server configuration loaded successfully");
            AppConfig appConfig = new AppConfig();
            HttpServer webServer = appConfig.httpServer();
            webServer.start();

        } catch (Exception ex) {
            logger.info("main() - Web Server could not start : {}", ex);
        }
    }
}


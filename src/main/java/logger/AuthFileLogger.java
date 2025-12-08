package logger;


import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
public class AuthFileLogger implements Logger {
    private static final String VALIDATION_FILE_PATH = "logs/validation.log";
    private static final String REQUESTS_FILE_PATH = "logs/validation.log";
    private static final String AUTH_FILE_PATH = "logs/auth.log";



    @Override
    public void write(String text) {
        try (FileWriter writer = new FileWriter(VALIDATION_FILE_PATH, true)) {
            writer.write(text + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Error writing to log file", e);
        }
    }
}

package org.moodle.springlaboratorywork.logger;

import lombok.RequiredArgsConstructor;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class FileLogger implements Logger {
    private final String filePath;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");


    @Override
    public void write(String level, String message, Class<?> clazz) {

        String logLine = String.format(
                "%s [%s] %-5s %s - %s\n",
                LocalDateTime.now().format(FORMATTER),
                Thread.currentThread().getName(),
                level,
                clazz.getSimpleName(),
                message
        );

        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(logLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

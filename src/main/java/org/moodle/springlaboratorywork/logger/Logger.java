package org.moodle.springlaboratorywork.logger;

public interface Logger {
    void write(String level, String message, Class<?> clazz);

}

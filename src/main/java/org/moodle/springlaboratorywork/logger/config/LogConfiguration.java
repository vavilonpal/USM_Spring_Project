package org.moodle.springlaboratorywork.logger.config;


import org.moodle.springlaboratorywork.logger.FileLogger;
import org.moodle.springlaboratorywork.logger.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfiguration {
    @Value("${logs.path.auth}")
    private String authLogfilePath;

    @Value("${logs.path.validation}")
    private String validationLogFilePath;

    @Value("${logs.path.requests}")
    private String requestsLogFilePath;
    @Value("${logs.path.responses}")
    private String responsesLogFilePath;

    @Bean
    public Logger authLogger(){
        return new FileLogger(authLogfilePath);
    }

    @Bean
    public Logger validationLogger(){
        return new FileLogger(validationLogFilePath);
    }

    @Bean
    public Logger requestsLogger(){
        return new FileLogger(requestsLogFilePath);
    }
    @Bean
    public Logger responsesLogger(){
        return new FileLogger(responsesLogFilePath);
    }

}

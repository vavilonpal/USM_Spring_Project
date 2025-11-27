package org.moodle.springlaboratorywork.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class ReadDataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.read")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }
}

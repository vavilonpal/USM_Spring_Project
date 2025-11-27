package org.moodle.springlaboratorywork.config.flyway;


import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class FlywayConfig {
    private final DataSource writeDataSource;
    private final DataSource readDataSource;
    @Bean(initMethod = "migrate")
    @ConfigurationProperties("spring.custom-flyway.write")
    public Flyway flywayWrite() {
        return Flyway.configure()
                .locations("classpath:db/migration")
                .dataSource(writeDataSource)
                .load();
    }

    @Bean(initMethod = "migrate")
    @ConfigurationProperties("spring.custom-flyway.read")
    public Flyway flywayRead() {
        return Flyway.configure()
                .locations("classpath:db/migration")
                .dataSource(readDataSource)
                .load();
    }

}

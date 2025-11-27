package org.moodle.springlaboratorywork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringLaboratoryWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringLaboratoryWorkApplication.class, args);
    }

}

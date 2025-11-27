package org.moodle.springlaboratorywork.aspect.log.repository;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class JpaWriteLoggingAspect {
    @Before("execution(* org.moodle.springlaboratorywork..write.repository..*.save*(..)) || " +
            "execution(* org.moodle.springlaboratorywork..write.repository..*.delete*(..)) || " +
            "execution(* org.moodle.springlaboratorywork..write.repository..*.update*(..))")
    public void logRead(JoinPoint joinPoint) {
        log.info("[JPA WRITE] {}", joinPoint.getSignature());
    }
}

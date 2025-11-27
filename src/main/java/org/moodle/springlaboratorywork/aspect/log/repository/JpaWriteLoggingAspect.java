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
    @Before("execution(* org.moodle.springlaboratorywork.repository.write..*.save*(..)) || " +
            "execution(* org.moodle.springlaboratorywork.repository.write..*.delete*(..)) || " +
            "execution(* org.moodle.springlaboratorywork.repository.write..*.update*(..))")
    public void logRead(JoinPoint joinPoint) {
        log.info("[JPA WRITE] {}", joinPoint.getSignature());
    }
}

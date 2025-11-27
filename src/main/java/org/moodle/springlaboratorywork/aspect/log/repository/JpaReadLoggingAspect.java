package org.moodle.springlaboratorywork.aspect.log.repository;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class JpaReadLoggingAspect {
    @Before("execution(* org.moodle.springlaboratorywork.repository.read..*.find*(..)) || " +
            "execution(* org.moodle.springlaboratorywork.repository.read..*.get*(..)) || " +
            "execution(* org.moodle.springlaboratorywork.repository.read..*.query*(..))")
    public void logRead(JoinPoint joinPoint) {
        log.info("[JPA READ] {}", joinPoint.getSignature());
    }
}

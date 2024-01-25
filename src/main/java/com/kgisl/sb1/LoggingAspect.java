package com.kgisl.sb1;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
 
@Aspect
@Component
public class LoggingAspect {
 
    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    @Before("execution(* com.kgisl.sb1..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Before method execution: " + joinPoint.getSignature().toShortString());
    }
 
    @After("execution(* com.kgisl.sb1..*(..))")
    public void logAfter(JoinPoint joinPoint) {
        logger.info("After method execution: " + joinPoint.getSignature().toShortString());
    }

    @AfterThrowing(pointcut = "execution(* com.kgisl.sb1..*(..))", throwing = "ex")
    public void afterThrowingAdvice(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().toShortString();

        if (ex.getClass().getName().equals("org.h2.jdbc.JdbcSQLNonTransientConnectionException")) {
            // Handle the specific exception
            logger.info("Connection exception in method '{}': {}", methodName, ex.getMessage());
            // Additional handling if needed
        } else {
            // Log other exceptions at the error level
            logger.info("Exception in method '{}': {}", methodName, ex.getMessage(), ex);
        }
    }
}
//controller=>aspect(creates proxy object)=>
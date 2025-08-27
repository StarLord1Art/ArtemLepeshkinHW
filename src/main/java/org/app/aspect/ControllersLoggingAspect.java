package org.app.aspect;

import java.time.Instant;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ControllersLoggingAspect {

  private static final Logger log = LoggerFactory.getLogger(ControllersLoggingAspect.class);

  @Before("execution( * org.app.controller.*.*(..))")
  public void logBeforeMethodCall(JoinPoint joinPoint) {
    log.info("Метод {} вызван", joinPoint.getSignature().getName());
  }

  @Around("execution( * org.app.controller.*.*(..))")
  public Object methodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    Instant startTime = Instant.now();
    Object result = joinPoint.proceed();
    Instant endTime = Instant.now();
    double duration = endTime.compareTo(startTime) / 1_000_000.0;
    log.info("Время выполнения метода {}: {} мс", joinPoint.getSignature().getName(), duration);
    return result;
  }
}
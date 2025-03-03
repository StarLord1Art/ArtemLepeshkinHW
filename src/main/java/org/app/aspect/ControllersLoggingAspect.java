package org.app.aspect;

import java.time.Instant;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@Getter
public class ControllersLoggingAspect {
  private int executionCount = 0;

  @Before("execution( * org.app.controller.*.*(..))")
  public void logBeforeMethodCall(JoinPoint joinPoint) {
    log.info("Метод {} вызван", joinPoint.getSignature().getName());
  }

  @Around("execution( * org.app.controller.*.*(..))")
  public Object methodExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
    executionCount++;
    Instant startTime = Instant.now();
    Object result = joinPoint.proceed();
    Instant endTime = Instant.now();
    double duration = endTime.compareTo(startTime) / 1_000_000.0;
    log.info("Время выполнения метода {}: {} мс", joinPoint.getSignature().getName(), duration);
    executionCount++;
    return result;
  }
}
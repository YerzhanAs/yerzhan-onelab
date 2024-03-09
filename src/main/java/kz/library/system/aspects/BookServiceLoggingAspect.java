package kz.library.system.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BookServiceLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceLoggingAspect.class);

    @Around("execution(* kz.library.system.services.impl.BookServiceImpl.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        String methodName = joinPoint.getSignature().getName();
        logger.info("Method {} executed in {} milliseconds", methodName, executionTime);

        return result;
    }
}

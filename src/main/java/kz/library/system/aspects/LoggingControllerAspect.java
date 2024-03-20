package kz.library.system.aspects;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
public class LoggingControllerAspect {

    @Before("execution(* kz.library.system.controllers.*.*(..))")
    public void logRequestDetails(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();

            log.info("Incoming request {} {} from IP: {} ",
                    request.getMethod(), request.getRequestURL().toString(),
                    request.getRemoteAddr());
        }
    }

    @AfterReturning(value = "execution(* kz.library.system.controllers.*.*(..))", returning = "result")
    public void logResponseDetails(JoinPoint joinPoint, Object result) {
        log.info("Method executed {} with return value: {}",
                joinPoint.getSignature().toShortString(), result.toString());
    }
}

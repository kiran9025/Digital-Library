package org.digitalLibrary.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AspectClassfordl {
    private final Logger logger = LoggerFactory.getLogger(AspectClassfordl.class);


    @Around("execution(* org.digitalLibrary.Controller.*(..))")
    public  Object  Methodcall(ProceedingJoinPoint joinPoint) throws Throwable{
        long startime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endtime = System.currentTimeMillis();
        long duration = (endtime - startime);

        logger.info("{} Method called with {} args {} ms",joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()),duration);
        return result;
    }

}

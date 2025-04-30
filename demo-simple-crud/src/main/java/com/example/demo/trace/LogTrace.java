package com.example.demo.trace;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Aspect
@Component
public class LogTrace {

    private static final ThreadLocal<String> traceIdHolder = ThreadLocal.withInitial(()
            -> UUID.randomUUID().toString().substring(0, 8));
    private static final ThreadLocal<Integer> depthHolder = ThreadLocal.withInitial(()
            -> 0);

    @Around("execution(* com.example..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        String traceId = traceIdHolder.get();
        int depth = depthHolder.get();
        String indent = createIndent(depth);
        String method = joinPoint.getSignature().toShortString();
        long startTime = System.currentTimeMillis();

        log.info("[{}] {}--> {}", traceId, indent, method);

        depthHolder.set(depth + 1);
        try{
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - startTime;

            depthHolder.set(depth);
            log.info("[{}] {}<-- {} => {} ({}ms)", traceId, indent, method, result, duration);
            return result;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            depthHolder.set(depth);
            log.error("[{}] {}<X- {} ({}ms)", traceId, indent, method, duration, e);
            throw e;
        } finally {
            if(depth == 0) {
                traceIdHolder.remove();
                depthHolder.remove();
            }
        }
    }

    private String createIndent(int depth) {
        return "|  ".repeat(Math.max(0, depth));
    }
}

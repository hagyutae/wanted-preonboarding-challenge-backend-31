package com.example.cqrsapp.aop;

import com.example.cqrsapp.common.exception.ConflictException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Aspect
@Component
@Order(Ordered.LOWEST_PRECEDENCE - 10) // 트랜잭션보다 나중에 실행되도록
public class DuplicateKeyExceptionAspect {

    @Around("@annotation(com.example.cqrsapp.aop.HandleDuplicateKey) && execution(* com.example.cqrsapp..service..*(..))")
    public Object handleDuplicateKey(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (DataIntegrityViolationException e) {
            if (isDuplicateKeyException(e)) {
                DuplicateKeyInfo info = extractDuplicateKeyInfo(e);
                if (info != null) {
                    throw new ConflictException(info.field(), info.value(), info.fullMessage(), e);
                }
            }
            throw e;
        }
    }

    private boolean isDuplicateKeyException(Throwable e) {
        Throwable cause = e;
        while (cause != null) {
            String msg = cause.getMessage();
            if (msg != null && msg.toLowerCase().contains("duplicate key value") && msg.contains("already exists")) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }

    private DuplicateKeyInfo extractDuplicateKeyInfo(Throwable e) {
        Throwable cause = e;
        while (cause != null) {
            String msg = cause.getMessage();
            if (msg != null) {
                Pattern pattern = Pattern.compile("Key \\(([^)]+)\\)=\\(([^)]+)\\)");
                Matcher matcher = pattern.matcher(msg);
                if (matcher.find()) {
                    String field = matcher.group(1);  // slug
                    String value = matcher.group(2);  // super-comfortable-sofa1
                    return new DuplicateKeyInfo(field, value, msg);
                }
            }
            cause = cause.getCause();
        }
        return null;
    }

    // 내부 레코드 객체로 중복 키 정보 담기
    private record DuplicateKeyInfo(String field, String value, String fullMessage) {}
}
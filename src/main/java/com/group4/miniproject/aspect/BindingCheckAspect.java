package com.group4.miniproject.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class BindingCheckAspect {
    @Pointcut("@annotation(com.group4.miniproject.annotation.BindingCheck)")
    public void bindingCheck() {
    }

    /*
    * @Around
    * 지정된 패턴에 해당하는 메소드의 실행되기 전, 실행된 후 모두에서 동작한다.
    * 이 어노테이션이 붙은 메소드의 반환 값은 Object여야 한다
    * */
    @Around("bindingCheck()")
    public Object aroundBindingCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof org.springframework.validation.BindingResult bindingResult) {
                if (bindingResult.hasErrors()) {
                    return new ResponseEntity<>(bindingResult.getAllErrors().get(0).getDefaultMessage(), org.springframework.http.HttpStatus.BAD_REQUEST);
                }
            }
        }
        return joinPoint.proceed();
    }
}

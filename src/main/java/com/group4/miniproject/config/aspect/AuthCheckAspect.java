package com.group4.miniproject.config.aspect;

import com.group4.miniproject.dto.PrincipalDto;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthCheckAspect {
    @Pointcut("@annotation(com.group4.miniproject.annotation.AuthCheck)")
    public void authCheck() {
    }

    @Before("authCheck()&&args(principal,..)")
    public void beforeAuthCheck(PrincipalDto principal) throws IllegalAccessException {
        if (principal == null) {
            throw new IllegalAccessException("로그인이 필요합니다.");
        }
    }
}

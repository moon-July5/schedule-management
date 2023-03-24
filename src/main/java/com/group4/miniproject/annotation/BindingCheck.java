package com.group4.miniproject.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(java.lang.annotation.ElementType.METHOD) // 메서드 단위로
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME) // 런타임 시 까지 유지
public @interface BindingCheck {
}

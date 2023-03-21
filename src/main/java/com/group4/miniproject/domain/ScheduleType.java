package com.group4.miniproject.domain;

import lombok.Getter;

@Getter
public enum ScheduleType {
    YEARLY("YEARLY"), // 연차
    DUTY("DUTY"), // 당직
    PLAN("PLAN"); // 일정

    private final String type;

    ScheduleType(String type){
        this.type = type;
    }
}

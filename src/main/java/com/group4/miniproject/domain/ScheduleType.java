package com.group4.miniproject.domain;

import lombok.Getter;

@Getter
public enum ScheduleType {
    YEARLY("YEARLY"),
    DUTY("DUTY");

    private final String type;

    ScheduleType(String type){
        this.type = type;
    }
}

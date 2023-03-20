package com.group4.miniproject.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@ToString
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Schedule extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @Setter
    private LocalDateTime startDate;

    @Setter
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Setter
    private ScheduleType type; // 연차, 당직

}

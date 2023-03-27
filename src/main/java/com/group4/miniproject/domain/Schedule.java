package com.group4.miniproject.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;


@ToString
@Getter
@Builder
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE schedule SET is_deleted = true, deleted_at=now() WHERE id = ?")
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

    @Setter
    private String content; // 일정 내용

    private LocalDateTime deletedAt;

    @Setter
    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;

}

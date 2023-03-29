package com.group4.miniproject.repository;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.domain.Schedule;
import com.group4.miniproject.domain.ScheduleType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT s " +
            "FROM Schedule s " +
            "WHERE s.type = 'DUTY' AND s.startDate >= :startDate AND s.endDate <= :endDate")
    List<Schedule> findDutySchedulesBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}

package com.group4.miniproject.repository;

import com.group4.miniproject.domain.Account;
import com.group4.miniproject.domain.SuccessLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SuccessLoginRepository extends JpaRepository<SuccessLogin,Long> {
    Optional<SuccessLogin> findByAccount(Account account);
}

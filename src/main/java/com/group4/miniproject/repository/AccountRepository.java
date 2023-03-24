package com.group4.miniproject.repository;

import com.group4.miniproject.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account,Long> {
    List<Account> findByName(String name);
    List<Account> findByAccountId(String accountId);
}

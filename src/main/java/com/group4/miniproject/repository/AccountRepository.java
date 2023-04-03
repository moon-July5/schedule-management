package com.group4.miniproject.repository;

import com.group4.miniproject.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
//    List<Account> findByName(String name);

    List<Account> findByName(String name);
    boolean existsByName(String name);

    Optional<Account> findByEmail(String email);
    boolean existsByEmail(String email);

    Optional<Account> findByAccountId(String accountId);
    boolean existsByAccountId(String accountId);

    List<Account> findByNameContainingIgnoreCase(String name);

}

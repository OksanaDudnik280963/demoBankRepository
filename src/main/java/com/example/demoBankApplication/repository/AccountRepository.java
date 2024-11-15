package com.example.demoBankApplication.repository;

import com.example.demoBankApplication.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
   Boolean existsByEmail(String email);
   Optional<Account> findBySortCodeAndAccountNumber(String sortCode, String accountNumber);
   Optional<Account> findByAccountNumber(String accountNumber);

}

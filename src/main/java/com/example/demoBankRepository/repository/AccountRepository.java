package com.example.demoBankRepository.repository;

import com.example.demoBankRepository.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
   Boolean existsByEmail(String email);
   Optional<Account> findBySortCodeAndAccountNumber(String sortCode, String accountNumber);
   Optional<Account> findByAccountNumber(String accountNumber);

}

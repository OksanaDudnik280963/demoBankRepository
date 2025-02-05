package com.example.demoBankRepository.repository;


import com.example.demoBankRepository.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findTransactionBySourceAccountSortCodeAndSourceAccountNumber
            (String sourceAccountSortCode, String sourceAccountNumber);
    // All transactions for a given category - latest first
    List<Transaction>  findTransactionByCategoryOrderByCategoryDesc(String category);
    // Total outgoing per category

}

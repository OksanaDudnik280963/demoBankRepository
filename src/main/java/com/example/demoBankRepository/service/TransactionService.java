package com.example.demoBankRepository.service;

import com.example.demoBankRepository.dto.BankResponse;
import com.example.demoBankRepository.dto.TransactionRequest;
import com.example.demoBankRepository.entity.Account;
import com.example.demoBankRepository.entity.Transaction;
import com.example.demoBankRepository.global.ACTION;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    BankResponse makeTransfer(TransactionRequest transactionInput);
    void updateAccountBalance(Account account, BigDecimal amount, ACTION action);
    boolean isAmountAvailable(BigDecimal amount, BigDecimal accountBalance);
    List<Transaction> findTransactionByCategoryOrderByCategoryDesc(String category);
    List<Transaction> findAll();
    PageImpl<Transaction> findPaginated(Pageable pageable);
    Transaction getByID(Long id);
}
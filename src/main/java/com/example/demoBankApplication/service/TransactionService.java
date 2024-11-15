package com.example.demoBankApplication.service;

import com.example.demoBankApplication.dto.BankResponse;
import com.example.demoBankApplication.dto.TransactionRequest;
import com.example.demoBankApplication.entity.Account;
import com.example.demoBankApplication.entity.Transaction;
import com.example.demoBankApplication.global.ACTION;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    BankResponse makeTransfer(TransactionRequest transactionInput);
    void updateAccountBalance(Account account, BigDecimal amount, ACTION action);
    boolean isAmountAvailable(BigDecimal amount, BigDecimal accountBalance);
    List<Transaction> findTransactionByCategoryOrderByCategoryDesc(String category);
    List<Transaction> findAll();
}
package com.example.demoBankRepository.controller;

import com.example.demoBankRepository.dto.BankResponse;
import com.example.demoBankRepository.dto.TransactionRequest;
import com.example.demoBankRepository.entity.Transaction;
import com.example.demoBankRepository.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BankResponse createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        return transactionService.makeTransfer(transactionRequest);
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Transaction> list() {
        return transactionService.findAll();
    }

    @GetMapping(value = "/list/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Transaction> listByCategory(@PathVariable String category) {
        return transactionService.findTransactionByCategoryOrderByCategoryDesc(category);
    }

}

package com.example.demoBankApplication.controller;

import com.example.demoBankApplication.dto.BankResponse;
import com.example.demoBankApplication.dto.TransactionRequest;
import com.example.demoBankApplication.entity.Transaction;
import com.example.demoBankApplication.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

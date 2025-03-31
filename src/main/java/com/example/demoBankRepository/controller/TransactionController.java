package com.example.demoBankRepository.controller;

import com.example.demoBankRepository.dto.BankResponse;
import com.example.demoBankRepository.dto.TransactionRequest;
import com.example.demoBankRepository.entity.Account;
import com.example.demoBankRepository.entity.Transaction;
import com.example.demoBankRepository.service.TransactionService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@Controller
@RequestMapping("/api/transactions")
public class TransactionController {
    public static final String ERR_MSG = "err";
    public static final String ERR_PAGE = "/errors/error";
    public static final String TRANSACTIONS_PAGE = "/transactions/transactionsList";
    public static final String EDIT_TRANSACTION = "/transactions/edit_transaction";
    public static final String NEW_TRANSACTION = "/transactions/new_transaction";
    public static final String REDIRECT = "redirect:";

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public BankResponse createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        return transactionService.makeTransfer(transactionRequest);
    }

/*
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Transaction> list() {
        return transactionService.findAll();
    }
*/

    @GetMapping(value = "/list", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView listAll(@RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        Page<Transaction> transactionsPage;
        transactionsPage = this.transactionService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        List<Transaction> transactions = (List<Transaction>) transactionsPage.getContent();
        ModelAndView modelAndView = new ModelAndView(TRANSACTIONS_PAGE);
        modelAndView.addObject("transactions", transactionsPage.getContent());
        modelAndView.addObject("currentPage", currentPage);
        modelAndView.addObject("pageSize", pageSize);
        modelAndView.addObject("transactionsPage", transactionsPage);
        modelAndView.addObject("pageNumbers", transactionsPage.getTotalPages());
        int totalPages = transactionsPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(java.util.stream.Collectors.toList());
            modelAndView.addObject("pageNumbers", pageNumbers);
        }
        try {
            transactions.forEach(usr ->
                    log.info(usr.getSourceAccountNumber() == null ? "no account number" : usr.getSourceAccountNumber())
            );
            return modelAndView;
        } catch (Exception ex) {
            String exMessage = ex.getMessage();
            ModelAndView model = new ModelAndView(ERR_PAGE);
            model.addObject(ERR_MSG, exMessage);
            log.error(exMessage);
            return model;
        }

    }

    @GetMapping(value = "/list/{category}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Transaction> listByCategory(@PathVariable String category) {
        return transactionService.findTransactionByCategoryOrderByCategoryDesc(category);
    }

    @GetMapping(value = "/new")
    public String showNewTransaction(Model model) {
        model.addAttribute("transaction", new Transaction());
        return NEW_TRANSACTION;
    }

    @PostMapping(value = "/new")
    public ModelAndView saveNewTransaction(@ModelAttribute("transaction") TransactionRequest transaction, Model model,
                                       @RequestParam("page") Optional<Integer> page,
                                       @RequestParam("size") Optional<Integer> size) throws Exception{

        BankResponse bankResponse = this.transactionService.makeTransfer(transaction);
        model.addAttribute("transaction", transaction);
        model.addAttribute("bankResponse", bankResponse);
        return listAll(page, size);
    }
    @GetMapping(value = "/edit/{id}")
    public ModelAndView editAccount (@ModelAttribute("transaction") Transaction transaction) throws Exception {
        ModelAndView modelAndView = new ModelAndView(EDIT_TRANSACTION);
        Transaction realTransaction = this.transactionService.getByID(transaction.getId());
        modelAndView.addObject("transaction", realTransaction);
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .type(realTransaction.getType())
                .targetOwnerName(realTransaction.getTargetOwnerName())
                .targetAccountSortCode(realTransaction.getTargetAccountSortCode())
                .targetAccountNumber(realTransaction.getTargetAccountNumber())
                .sourceAccountSortCode(realTransaction.getSourceAccountSortCode())
                .sourceAccountNumber(realTransaction.getSourceAccountNumber())
                .reference(realTransaction.getReference())
                .category(realTransaction.getCategory())
                .amount(realTransaction.getAmount())
                .build();
        this.transactionService.makeTransfer(transactionRequest);
        return modelAndView;
    }

    @PostMapping(value = "/edit")
    public ModelAndView save (@ModelAttribute("transaction") TransactionRequest transaction){

        ModelAndView modelAndView = new ModelAndView(EDIT_TRANSACTION);
        modelAndView.addObject("transaction", transaction);
        try {
            this.transactionService.makeTransfer(transaction);
        } catch (Exception ex) {
            String exMessage = ex.getMessage();
            ModelAndView model = new ModelAndView(ERR_PAGE);
            model.addObject(ERR_MSG, exMessage);
            log.error(exMessage);
            return model;
        }
        return listAll(Optional.of(1), Optional.of(10));
    }

}

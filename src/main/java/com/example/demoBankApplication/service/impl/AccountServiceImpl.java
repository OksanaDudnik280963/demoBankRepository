package com.example.demoBankApplication.service.impl;

import com.example.demoBankApplication.dto.AccountInfo;
import com.example.demoBankApplication.dto.BankResponse;
import com.example.demoBankApplication.dto.AccountRequest;
import com.example.demoBankApplication.entity.Transaction;
import com.example.demoBankApplication.entity.Account;
import com.example.demoBankApplication.repository.AccountRepository;
import com.example.demoBankApplication.repository.TransactionRepository;
import com.example.demoBankApplication.service.AccountService;
import com.example.demoBankApplication.utils.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static com.example.demoBankApplication.global.Constants.*;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    public AccountRepository accountRepository;

    @Autowired
    public TransactionRepository transactionRepository;


    /*saving a new user into db*/
    @Override
    public BankResponse createAccount(AccountRequest accountRequest) {
        CodeGenerator codeGenerator = new CodeGenerator();
        if (accountRepository.existsByEmail(accountRequest.getEmail())) {
            return BankResponse.builder()
                    .responseCode(ACCOUNT_EXISTS_CODE)
                    .responseMessage(ACCOUNT_EXISTS_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountBalance(accountRequest.getAccountBalance())
                            .accountNumber(accountRequest.getAccountNumber())
                            .accountName(accountRequest.getFirstName() + " " + accountRequest.getLastName() + " " + accountRequest.getOtherName())
                            .bankName(accountRequest.getBankName())
                            .sortCode(accountRequest.getSortCode())
                            .build())
                    .build();
        } else {
            Account newAccount = Account.builder()
                    .firstName(accountRequest.getFirstName())
                    .lastName(accountRequest.getLastName())
                    .otherName(accountRequest.getOtherName())
                    .gender(accountRequest.getGender())
                    .address(accountRequest.getAddress())
                    .stateOfOrigin(accountRequest.getStateOfOrigin())
                    .accountNumber(codeGenerator.generateAccountNumber())
                    .accountBalance(BigDecimal.ZERO)
                    .email(accountRequest.getEmail())
                    .phoneNumber(accountRequest.getPhoneNumber())
                    .sortCode(codeGenerator.generateSortCode())
                    .bankName(accountRequest.getBankName())
                    .transactions(new ArrayList<Transaction>())
                    .status("ACTIVE")
                    .build();
            Account savedAccount = accountRepository.save(newAccount);
            return BankResponse.builder()
                    .responseCode(ACCOUNT_CREATION_SUCCESS)
                    .responseMessage(ACCOUNT_CREATION_MESSAGE)
                    .accountInfo(AccountInfo.builder()
                            .accountBalance(savedAccount.getAccountBalance())
                            .accountNumber(savedAccount.getAccountNumber())
                            .accountName(savedAccount.getFirstName() + " " + savedAccount.getLastName() + " " + savedAccount.getOtherName())
                            .sortCode(savedAccount.getSortCode())
                            .bankName(savedAccount.getBankName())
                            .build())
                    .build();

        }
    }

    public Account getAccount(String sortCode, String accountNumber) {
        Optional<Account> account = accountRepository
                .findBySortCodeAndAccountNumber(sortCode, accountNumber);

        account.ifPresent(value ->
                value.setTransactions(transactionRepository.findTransactionBySourceAccountSortCodeAndSourceAccountNumber
                                                (value.getSortCode(), value.getAccountNumber())));


        return account.orElse(null);
    }



    public Account getAccount(String accountNumber) {
        Optional<Account> account = accountRepository
                .findByAccountNumber(accountNumber);

        return account.orElse(null);
    }



}

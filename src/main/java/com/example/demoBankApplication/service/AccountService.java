package com.example.demoBankApplication.service;

import com.example.demoBankApplication.dto.BankResponse;
import com.example.demoBankApplication.dto.AccountRequest;
import com.example.demoBankApplication.entity.Account;

public interface AccountService {
    BankResponse createAccount(AccountRequest accountRequest);
    Account getAccount(String sortCode, String accountNumber);
    Account getAccount(String accountNumber);
}

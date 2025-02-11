package com.example.demoBankRepository.service;

import com.example.demoBankRepository.dto.BankResponse;
import com.example.demoBankRepository.dto.AccountRequest;
import com.example.demoBankRepository.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AccountService {
    BankResponse createAccount(AccountRequest accountRequest);
    Account newAccount(AccountRequest accountRequest);
    Account getAccount(String sortCode, String accountNumber);
    Account getAccount(String accountNumber);
    List<Account> listAccounts();
    List<Account> findAll(Sort sort);
    Page<Account> findAll(Pageable pageable);
    Page<Account> findPaginated(Pageable pageable);
    Account save(AccountRequest account);
    void delete(Account account);
}

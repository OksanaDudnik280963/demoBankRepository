package com.example.demoBankRepository.service.impl;

import com.example.demoBankRepository.dto.AccountInfo;
import com.example.demoBankRepository.dto.BankResponse;
import com.example.demoBankRepository.dto.AccountRequest;
import com.example.demoBankRepository.entity.Transaction;
import com.example.demoBankRepository.entity.Account;
import com.example.demoBankRepository.entity.enums.Gender;
import com.example.demoBankRepository.entity.enums.StatusName;
import com.example.demoBankRepository.repository.AccountRepository;
import com.example.demoBankRepository.repository.TransactionRepository;
import com.example.demoBankRepository.service.AccountService;
import com.example.demoBankRepository.utils.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.demoBankRepository.global.Constants.*;

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
                            .accountBalance(new BigDecimal(accountRequest.getAccountBalance().toString()))
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
                    .accountBalance(new BigDecimal(accountRequest.getAccountBalance().toString()))
                    .email(accountRequest.getEmail())
                    .phoneNumber(accountRequest.getPhoneNumber())
                    .sortCode(codeGenerator.generateSortCode())
                    .bankName(accountRequest.getBankName())
                    .transactions(new ArrayList<Transaction>())/// ////
                    .status(accountRequest.getStatus())
                    .alternativePhoneNumber(accountRequest.getAlternativePhoneNumber())
                    .build();
            Account savedAccount = accountRepository.save(newAccount);
            return BankResponse.builder()
                    .responseCode(ACCOUNT_CREATION_SUCCESS_CODE)
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

    @Override
    public Account newAccount(AccountRequest accountRequest) {
        CodeGenerator codeGenerator = new CodeGenerator();
        if (accountRequest != null && !accountRequest.getEmail().isEmpty() && accountRepository.existsByEmail(accountRequest.getEmail())) {
            AccountInfo accountInfo = AccountInfo.builder()
                    .accountBalance(accountRequest.getAccountBalance())
                    .accountNumber(accountRequest.getAccountNumber())
                    .accountName(accountRequest.getFirstName() + " " + accountRequest.getLastName() + " " + accountRequest.getOtherName())
                    .bankName(accountRequest.getBankName())
                    .sortCode(accountRequest.getSortCode())
                    .build();

            Optional<Account> foundedAccount = accountRepository.findByAccountNumber(accountInfo.getAccountNumber());

            return foundedAccount.get();
        } else {
            Account newAccount = Account.builder()
                    .firstName(accountRequest.getFirstName())
                    .lastName(accountRequest.getLastName())
                    .otherName(accountRequest.getOtherName())
                    .gender(Gender.FEMALE)
                    .address(accountRequest.getAddress())
                    .stateOfOrigin(StatusName.ACTIVE)
                    .accountNumber(codeGenerator.generateAccountNumber())
                    .accountBalance(BigDecimal.ZERO)
                    .email(accountRequest.getEmail())
                    .phoneNumber(accountRequest.getPhoneNumber())
                    .sortCode(codeGenerator.generateSortCode())
                    .bankName("BANK1")
                    .transactions(new ArrayList<Transaction>())
                    .status(StatusName.ACTIVE)
                    .build();
            return accountRepository.save(newAccount);
        }
    }
    public Optional<Account> getAccountById(Long id){
        return this.accountRepository.findById(id);
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

    @Override
    public List<Account> listAccounts() {
        return accountRepository.findAll();
    }

    public List<Account> findAll(Sort sort) {
        //setTakRepository();
        return this.accountRepository.findAll(sort);
    }

    public Page<Account> findAll(Pageable pageable) {
        //setTakRepository();
        return this.accountRepository.findAll(pageable);
    }

    public Page<Account> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Account> list;
        List<Account> accounts = listAccounts();
        if (accounts.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, accounts.size());
            list = accounts.subList(startItem, toIndex);
        }

        return new PageImpl<Account>(list, PageRequest.of(currentPage, pageSize), accounts.size());
    }
    public Account save(Account account) throws Exception {
        if (account != null && account.getId() != null) {
            Account realAccount = this.accountRepository.findByAccountNumber(account.getAccountNumber()).get();
            realAccount.setAccountBalance(account.getAccountBalance());
            realAccount.setBankName(account.getBankName());
            realAccount.setFirstName(account.getFirstName());
            realAccount.setLastName(account.getLastName());
            realAccount.setAccountNumber(account.getAccountNumber());
            realAccount.setAddress(account.getAddress());
            realAccount.setAlternativePhoneNumber(account.getAlternativePhoneNumber());
            realAccount.setPhoneNumber(account.getPhoneNumber());
            realAccount.setEmail(account.getEmail());
            realAccount.setGender(account.getGender());
            realAccount.setSortCode(account.getSortCode());
            realAccount.setOtherName(account.getOtherName());
            realAccount.setStateOfOrigin(account.getStateOfOrigin());
            realAccount.setStatus(account.getStatus());
            return this.accountRepository.save(realAccount);
        }else{
            assert account != null;
            AccountRequest accountRequest = AccountRequest.builder()
                    .firstName(account.getFirstName())
                    .lastName(account.getLastName())
                    .otherName(account.getOtherName())
                    .gender(account.getGender())
                    .address(account.getAddress())
                    .stateOfOrigin(StatusName.ACTIVE)
                    .accountNumber(account.getAccountNumber())
                    .accountBalance(new BigDecimal(account.getAccountBalance().toString()))
                    .email(account.getEmail())
                    .phoneNumber(account.getPhoneNumber())
                    .sortCode(account.getSortCode())
                    .bankName(account.getBankName())
                    .status(StatusName.ACTIVE)
                    .build();
            BankResponse bankResponse = createAccount(accountRequest);

            Account saved = getAccount(bankResponse.getAccountInfo().getAccountNumber());
            if (bankResponse.getResponseCode().equals(ACCOUNT_CREATION_SUCCESS_CODE)) {
                return saved;
            }else{

               if(bankResponse.getResponseCode().equals(ACCOUNT_EXISTS_CODE)){
                   Account modified = getAccountFromAccountRequest(accountRequest);
                   return this.accountRepository.save(modified);
               }else {
                    throw new Exception(bankResponse.getResponseMessage());
               }
            }

        }

    }

    public Account save(AccountRequest account) {
        Account realAccount = getAccount(account.getAccountNumber());
        if (realAccount != null && realAccount.getId() != null) {
            realAccount.setAccountBalance(account.getAccountBalance());
            realAccount.setBankName(account.getBankName());
            realAccount.setFirstName(account.getFirstName());
            realAccount.setLastName(account.getLastName());
            realAccount.setAccountNumber(account.getAccountNumber());
            realAccount.setAddress(account.getAddress());
            realAccount.setAlternativePhoneNumber(account.getAlternativePhoneNumber());
            realAccount.setPhoneNumber(account.getPhoneNumber());
            realAccount.setEmail(account.getEmail());
            realAccount.setGender(account.getGender());
            realAccount.setSortCode(account.getSortCode());
            realAccount.setOtherName(account.getOtherName());
            realAccount.setStateOfOrigin(account.getStateOfOrigin());
            realAccount.setStatus(account.getStatus());

            return accountRepository.save(realAccount);
        } else {
            BankResponse bankResponse = createAccount(account);
            if (bankResponse.getResponseCode().equals(ACCOUNT_CREATION_SUCCESS_CODE)) {
                return getAccount(bankResponse.getAccountInfo().getAccountNumber());
            }

            if (bankResponse.getResponseCode().equals(ACCOUNT_EXISTS_CODE)
            ) {
                Account real = getAccountFromAccountRequest(account);
                return accountRepository.save(real);
            } else {
                return null;
            }
        }

    }

    @Override
    public void delete(Account account) {
        accountRepository.delete(account);
        return;
    }

    public Account getAccountFromAccountRequest(AccountRequest accountRequest) {
        Account realAccount = getAccount(accountRequest.getSortCode(), accountRequest.getAccountNumber());
        if (realAccount != null && realAccount.getId() != null) {

            realAccount.setAccountBalance(accountRequest.getAccountBalance());
            realAccount.setBankName(accountRequest.getBankName());
            realAccount.setFirstName(accountRequest.getFirstName());
            realAccount.setLastName(accountRequest.getLastName());
            realAccount.setAccountNumber(accountRequest.getAccountNumber());
            realAccount.setAddress(accountRequest.getAddress());
            realAccount.setAlternativePhoneNumber(accountRequest.getAlternativePhoneNumber());
            realAccount.setPhoneNumber(accountRequest.getPhoneNumber());
            realAccount.setEmail(accountRequest.getEmail());
            realAccount.setGender(accountRequest.getGender());
            realAccount.setSortCode(accountRequest.getSortCode());
            realAccount.setOtherName(accountRequest.getOtherName());
            realAccount.setStateOfOrigin(accountRequest.getStateOfOrigin());
            realAccount.setStatus(accountRequest.getStatus());
            return realAccount;
        } else {
            Account account = new Account();
            account.setAccountBalance(accountRequest.getAccountBalance());
            account.setBankName(accountRequest.getBankName());
            account.setFirstName(accountRequest.getFirstName());
            account.setLastName(accountRequest.getLastName());
            account.setAccountNumber(accountRequest.getAccountNumber());
            account.setAddress(accountRequest.getAddress());
            account.setAlternativePhoneNumber(accountRequest.getAlternativePhoneNumber());
            account.setPhoneNumber(accountRequest.getPhoneNumber());
            account.setSortCode(accountRequest.getSortCode());
            account.setOtherName(accountRequest.getOtherName());
            account.setStateOfOrigin(accountRequest.getStateOfOrigin());
            account.setStatus(accountRequest.getStatus());
            return account;
        }
    }
}

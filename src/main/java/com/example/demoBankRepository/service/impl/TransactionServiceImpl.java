package com.example.demoBankRepository.service.impl;

import com.example.demoBankRepository.dto.AccountInfo;
import com.example.demoBankRepository.dto.BankResponse;
import com.example.demoBankRepository.dto.TransactionRequest;
import com.example.demoBankRepository.entity.Transaction;
import com.example.demoBankRepository.entity.Account;
import com.example.demoBankRepository.entity.enums.TransactionType;
import com.example.demoBankRepository.global.ACTION;
import com.example.demoBankRepository.global.Constants;
import com.example.demoBankRepository.repository.TransactionRepository;
import com.example.demoBankRepository.repository.AccountRepository;
import com.example.demoBankRepository.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;


    public BankResponse makeTransfer(TransactionRequest transactionInput) {

        String sourceSortCode = transactionInput.getSourceAccountSortCode();
        String sourceAccountNumber = transactionInput.getSourceAccountNumber();

        Optional<Account> sourceAccount = accountRepository
                .findBySortCodeAndAccountNumber(sourceSortCode, sourceAccountNumber);

        String targetSortCode = transactionInput.getTargetAccountSortCode();
        String targetAccountNumber = transactionInput.getTargetAccountNumber();

        Optional<Account> targetAccount =
                accountRepository.findBySortCodeAndAccountNumber(targetSortCode, targetAccountNumber);

        if (sourceAccount.isPresent() && targetAccount.isPresent()) {
            if (isAmountAvailable(transactionInput.getAmount(),
                    sourceAccount.get().getAccountBalance())) {
                Transaction transaction = Transaction.builder()
                        .amount(transactionInput.getAmount())
                        .sourceAccountSortCode(sourceAccount.get().getSortCode())
                        .sourceAccountNumber(sourceAccount.get().getAccountNumber())
                        .targetAccountSortCode(targetAccount.get().getSortCode())
                        .targetAccountNumber(targetAccount.get().getAccountNumber())
                        .targetOwnerName(targetAccount.get().getFirstName() + " " + targetAccount.get().getLastName() + " " + targetAccount.get().getOtherName())
                        .category(transactionInput.getCategory())
                        .type(transactionInput.getType())
                        .reference(transactionInput.getReference())
                        .build();

                transactionRepository.save(transaction);

                updateAccountBalance(sourceAccount.get(), transactionInput.getAmount(), ACTION.WITHDRAW);
                updateAccountBalance(targetAccount.get(), transactionInput.getAmount(), ACTION.DEPOSIT);

                return BankResponse.builder()
                        .responseCode(Constants.OPERATION_COMPLETED_SUCCESS)
                        .responseMessage(Constants.SUCCESS)
                        .accountInfo(AccountInfo.builder()
                                .accountNumber(sourceAccount.get().getAccountNumber())
                                .bankName(sourceAccount.get().getBankName())
                                .accountName(sourceAccount.get().getFirstName()
                                        + " " + sourceAccount.get().getLastName() + " " + sourceAccount.get().getOtherName())
                                .sortCode(sourceAccount.get().getSortCode())
                                .accountBalance(sourceAccount.get().getAccountBalance())
                                .build())
                        .build();

            } else {
                return BankResponse.builder()
                        .responseCode(Constants.OPERATION_INSUFFICIENT_ACCOUNT_BALANCE)
                        .responseMessage(Constants.INSUFFICIENT_ACCOUNT_BALANCE)
                        .accountInfo(AccountInfo.builder()
                                .accountNumber(sourceAccount.get().getAccountNumber())
                                .bankName(sourceAccount.get().getBankName())
                                .accountName(sourceAccount.get().getFirstName()
                                        + " " + sourceAccount.get().getLastName() + " " + sourceAccount.get().getOtherName())
                                .sortCode(sourceAccount.get().getSortCode())
                                .accountBalance(sourceAccount.get().getAccountBalance())
                                .build())
                        .build();

            }
        }
        return BankResponse.builder()
                .responseCode(Constants.OPERATION_NO_ACCOUNT_FOUND)
                .responseMessage(Constants.NO_ACCOUNT_FOUND)
                .accountInfo(AccountInfo.builder()
                        .bankName("")
                        .sortCode("")
                        .accountBalance(new BigDecimal("0.0"))
                        .accountName("")
                        .accountNumber("")
                        .build())
                .build();
    }

    public void updateAccountBalance(Account account, BigDecimal amount, ACTION action) {
        if (action == ACTION.WITHDRAW) {
            account.setAccountBalance((account.getAccountBalance().subtract(amount)));
        } else if (action == ACTION.DEPOSIT) {
            account.setAccountBalance((account.getAccountBalance().add(amount)));
        }
        accountRepository.save(account);
    }

    public boolean isAmountAvailable(BigDecimal amount, BigDecimal accountBalance) {
        double sum = accountBalance.subtract(amount).doubleValue();
        return sum >= 0.0;
    }

    public List<Transaction> findTransactionByCategoryOrderByCategoryDesc(String category) {
        return transactionRepository.findTransactionByCategoryOrderByCategoryDesc(category);
    }

    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }
}

package com.example.demoBankApplication.utils;


import com.example.demoBankApplication.dto.AccountRequest;
import com.example.demoBankApplication.dto.TransactionRequest;
import com.example.demoBankApplication.entity.Account;
import com.example.demoBankApplication.global.*;

public class UserAccountValidator {

    public UserAccountValidator() {
    }

    public static boolean isSearchCriteriaValid(String sortCode, String accountNumber) {

        return Constants.SORT_CODE_PATTERN.matcher(sortCode).find() &&
                Constants.ACCOUNT_NUMBER_PATTERN.matcher(accountNumber).find();
    }

    public static boolean isAccountNoValid(String accountNo) {
        return Constants.ACCOUNT_NUMBER_PATTERN.matcher(accountNo).find();
    }

    public static boolean isCreateAccountCriteriaValid(Account createAccountInput) {
        String ownerName = createAccountInput.getFirstName()+" "+createAccountInput.getLastName()+" "+createAccountInput.getOtherName();
        return (!createAccountInput.getBankName().isBlank() && !ownerName.isBlank());
    }

    public static boolean isSearchTransactionValid(TransactionRequest transactionInput) {

        if (!isSearchCriteriaValid(transactionInput.getSourceAccountSortCode(), transactionInput.getSourceAccountNumber()))
            return false;


        if (!isSearchCriteriaValid(transactionInput.getTargetAccountSortCode(), transactionInput.getTargetAccountNumber()))
            return false;

        if (transactionInput.getSourceAccountNumber().equals(transactionInput.getTargetAccountNumber())
                &&
                transactionInput.getSourceAccountSortCode().equals(transactionInput.getTargetAccountSortCode())
        )
            return false;


        return true;
    }
}

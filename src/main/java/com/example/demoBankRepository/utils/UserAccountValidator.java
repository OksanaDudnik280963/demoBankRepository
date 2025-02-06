package com.example.demoBankRepository.utils;


import com.example.demoBankRepository.dto.TransactionRequest;
import com.example.demoBankRepository.entity.Account;
import com.example.demoBankRepository.global.*;

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

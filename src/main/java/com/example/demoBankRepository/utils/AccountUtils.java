package com.example.demoBankRepository.utils;

import java.time.Year;

public class AccountUtils {


    public AccountUtils() {
    }

    public static String generateAccountNumber() {

        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;
        int randNumber = (int) Math.floor(Math.random() * (max - min + 1));
        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randNumber);
        return year + randomNumber;
    }

}

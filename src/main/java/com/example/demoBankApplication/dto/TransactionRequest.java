package com.example.demoBankApplication.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TransactionRequest {

    private String sourceAccountSortCode;
    private String sourceAccountNumber;

    private String targetAccountSortCode;
    private String targetAccountNumber;
    private String targetOwnerName;

    private BigDecimal amount;
    private String reference;

    private String category;
    private String type;// card, direct deposit, internet

}

package com.example.demoBankRepository.dto;

import com.example.demoBankRepository.entity.enums.Gender;
import com.example.demoBankRepository.entity.enums.StatusName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AccountRequest {
    private String firstName;
    private String lastName;
    private String otherName;
    private Gender gender;
    private String address;
    private StatusName stateOfOrigin;
    private String accountNumber;
    private BigDecimal accountBalance;
    private String email;
    private String phoneNumber;
    private String alternativePhoneNumber;
    private StatusName status;
    private String sortCode;
    private String bankName;

}

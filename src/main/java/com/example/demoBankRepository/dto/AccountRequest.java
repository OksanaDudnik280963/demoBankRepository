package com.example.demoBankRepository.dto;

import com.example.demoBankRepository.entity.enums.Gender;
import com.example.demoBankRepository.entity.enums.StatusName;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AccountRequest {
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    private String otherName;
    private Gender gender;
    private String address;
    private StatusName stateOfOrigin;
    @NonNull
    private String accountNumber;
    private BigDecimal accountBalance;
    @NonNull
    private String email;
    @NonNull
    private String phoneNumber;
    private String alternativePhoneNumber;
    private StatusName status;
    @NonNull
    private String sortCode;
    @NonNull
    private String bankName;

}

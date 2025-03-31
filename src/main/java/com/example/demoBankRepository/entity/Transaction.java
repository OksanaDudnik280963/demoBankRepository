package com.example.demoBankRepository.entity;

import com.example.demoBankRepository.entity.enums.TransactionType;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@Data
@Entity
@Table(name = "transactions", schema="my_bank")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "SOURCE_ACCOUNT_SORT_CODE")
    private String sourceAccountSortCode;

    @Column(name = "SOURCE_ACCOUNT_NUMBER")
    private String sourceAccountNumber;

    @Column(name = "TARGET_ACCOUNT_SORT_CODE")
    private String targetAccountSortCode;

    @Column(name = "TARGET_ACCOUNT_NUMBER")
    private String targetAccountNumber;

    @NotBlank(message = "Owner name is mandatory")
    @Column(name = "TARGET_OWNER_NAME")
    private String targetOwnerName;//vendor


    @Positive(message = "Transfer amount must be positive")
    @Min(value = 1, message = "Amount must be larger than 1")
    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "REFERENCE")
    private String reference;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "TYPE")
    @Enumerated(EnumType.ORDINAL)
    private TransactionType type;// card, direct deposit, internet

    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime initiationDate;
    @UpdateTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime completionDate;
}

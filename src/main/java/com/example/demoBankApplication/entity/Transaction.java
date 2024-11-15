package com.example.demoBankApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


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
@Table(name = "transactions")
@SequenceGenerator(name = "transaction_seq", sequenceName = "transaction_sequence", initialValue = 5)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String sourceAccountSortCode;
    private String sourceAccountNumber;

    private String targetAccountSortCode;
    private String targetAccountNumber;

    @NotBlank(message = "Owner name is mandatory")
    private String targetOwnerName;//vendor


    @Positive(message = "Transfer amount must be positive")
    @Min(value = 1, message = "Amount must be larger than 1")
    private BigDecimal amount;
    private String reference;
    private String category;
    private String type;// card, direct deposit, internet

    @CreationTimestamp
    private LocalDateTime initiationDate;
    @UpdateTimestamp
    private LocalDateTime completionDate;
}

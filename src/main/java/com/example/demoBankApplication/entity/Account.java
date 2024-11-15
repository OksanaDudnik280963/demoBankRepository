package com.example.demoBankApplication.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@Entity
@Data
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;

    private String firstName;
    private String lastName;
    private String otherName;
    private String gender;
    private String address;

    private String email;
    private String phoneNumber;
    private String alternativePhoneNumber;
    private String status;

    private String stateOfOrigin;

    @NotBlank(message = "Sort code is mandatory")
    private String sortCode;
    @NotBlank(message = "Account number is mandatory")
    private String accountNumber;
    @NotBlank(message = "Bank name is mandatory")
    private String bankName;    
    private BigDecimal accountBalance;

    //@OneToMany(fetch = FetchType.EAGER, targetEntity = Account.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private transient List<Transaction> transactions;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime modifyAt;
}

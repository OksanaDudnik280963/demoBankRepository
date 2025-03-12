package com.example.demoBankRepository.entity;

import com.example.demoBankRepository.entity.enums.Gender;
import com.example.demoBankRepository.entity.enums.StatusName;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
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
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is mandatory")
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "OTHER_NAME", nullable = true)
    private String otherName;

    @Column(name = "GENDER", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{0,17}$", message = "Phone number")
    @Size(max = 17)//+447492555481
    @Column(name = "PHONE_NUMBER", unique = true, nullable = false)
    private String phoneNumber;

    @Pattern(regexp = "^\\+?[0-9]{0,17}$", message = "Alternative Phone number")
    @Size(max = 17)
    @Column(name = "ALTERNATIVE_PHONE_NUMBER", nullable = true)
    private String alternativePhoneNumber;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StatusName status;

    @Column(name = "STATE_OF_ORIGIN", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StatusName stateOfOrigin;

    @NotBlank(message = "Sort code is mandatory")
    @Column(name = "SORT_CODE", unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]{2}-[0-9]{2}-[0-9]{2}$", message = "Sort code")
    private String sortCode;

    @NotBlank(message = "Account number is mandatory")
    @Column(name = "ACCOUNT_NUMBER", unique = true, nullable = false)
    @Pattern(regexp = "^[0-9]{8}$", message = "Sort code")
    private String accountNumber;

    @NotBlank(message = "Bank name is mandatory")
    @Column(name = "BANK_NAME", nullable = false)
    private String bankName;

    @Positive(message = "BALANCE amount must be 0 or positive")
    @Min(value = 0, message = "BALANCE amount must be larger than -1")
    @Column(name = "ACCOUNT_BALANCE", nullable = false)
    private BigDecimal accountBalance;

    //@OneToMany(fetch = FetchType.EAGER, targetEntity = Account.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private transient List<Transaction> transactions;

    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDateTime modifyAt;
}

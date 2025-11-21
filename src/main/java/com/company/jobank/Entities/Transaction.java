package com.company.jobank.Entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import com.company.jobank.Enums.TransactionType;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Счет, с которого операция (отправитель)
    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    // Счет, на который операция (получатель, может быть null при снятии)
    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount;

    // Тип операции
    @Enumerated(EnumType.STRING)
    @NotNull
    private TransactionType type; // DEPOSIT, WITHDRAW, TRANSFER

    @NotNull
    private Double amount;

    @CreationTimestamp
    private LocalDateTime transactionDate;

    private String description; // опционально
}
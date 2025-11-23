package com.company.jobank.Dtos;


import com.company.jobank.Enums.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private Long id;
    private Long fromAccountId;       // вместо объекта Account
    private Long toAccountId;
    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDateTime transactionDate;
}
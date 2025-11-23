package com.company.jobank.Dtos;


import com.company.jobank.Enums.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class AccountDto {
    private Long id;
    private Long userId;              // вместо всего объекта User
    private String userFullName;      // удобно для фронтенда
    private Double balance;
}
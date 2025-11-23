package com.company.jobank.Dtos;


import lombok.Data;


@Data
public class AccountDto {
    private Long id;
    private Long userId;
    private String userFullName;      // for frontends_)
    private Double balance;
}
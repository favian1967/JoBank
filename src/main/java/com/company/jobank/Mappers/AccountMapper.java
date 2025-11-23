package com.company.jobank.Mappers;


import com.company.jobank.Dtos.AccountDto;
import com.company.jobank.Entities.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountDto toDTO(Account account) {
        if (account == null) {
            return null;
        }

        AccountDto dto = new AccountDto();
        dto.setId(account.getId());
        dto.setUserId(account.getUser().getId());
        dto.setUserFullName(
                account.getUser().getFirstName() + " " +
                        account.getUser().getLastName()
        );
        dto.setBalance(account.getBalance());
        return dto;
    }


    public Account toEntity(AccountDto dto) {
        if (dto == null) {
            return null;
        }

        Account account = new Account();
        account.setId(dto.getId());
        account.setBalance(dto.getBalance());
        return account;
    }
}

package com.company.jobank.Controllers;


import com.company.jobank.Dtos.AccountDto;
import com.company.jobank.Entities.Account;
import com.company.jobank.Mappers.AccountMapper;
import com.company.jobank.Repositories.AccountRepository;
import com.company.jobank.Services.AccountService;
import com.company.jobank.exceptions.AccountNotFoundException;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accounts")
public class AccountController {


    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    public AccountController(AccountRepository accountRepository, AccountService accountService, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
        this.accountMapper = accountMapper;
    }

    @GetMapping("/getBalance")
    public Double getBalance(
            @RequestParam Long userId
    ) {
        return accountService.getBalance(userId);
    }

    @PutMapping("/deposit")
    public Double deposit(
            @RequestParam Long userId,
            @RequestParam Double amount
    ) {
//        Account account = accountService.deposit(userId, amount);
//        return ResponseEntity.ok(account);
        return accountService.deposit(userId, amount); //I like it)
    }

    @PutMapping("/withdraw")
    public String withdraw(
            @RequestParam Long userId,
            @RequestParam Double amount
    ) {
        return accountService.withdraw(userId, amount);
    }


    @PutMapping("/transfer")
    public String transferMoney(
            @RequestParam Long transferFrom,
            @RequestParam Long transferTo,
            @RequestParam Double amount
    ) {
        return accountService.transfer(transferFrom, transferTo, amount);
    }

    @GetMapping
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/id")
    public AccountDto getAccountById(@RequestParam Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        return accountMapper.toDTO(account);
    }

    @GetMapping("/byUser")
    public AccountDto getAccountByUserId(@RequestParam Long userId) {
     Account account = accountRepository.findByUserId(userId)
             .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return accountMapper.toDTO(account);
    }


    //delete only account
    @DeleteMapping("/delete")
    public String deleteAccount(@RequestParam Long accountId) {
        accountRepository.deleteById(accountId);
        return "Account deleted";
    }


}
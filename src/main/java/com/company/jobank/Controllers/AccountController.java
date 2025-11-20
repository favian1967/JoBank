package com.company.jobank.Controllers;


import com.company.jobank.Entities.Account;
import com.company.jobank.Repositories.AccountRepository;
import com.company.jobank.Services.AccountService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {


    private final AccountRepository accountRepository;
    private final AccountService accountService;

    public AccountController(AccountRepository accountRepository, AccountService accountService) {
        this.accountRepository = accountRepository;
        this.accountService = accountService;
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
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/id")
    public Account getAccountById(@RequestParam Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }

    @GetMapping("/byUser")
    public Account getAccountByUserId(@RequestParam Long userId) {
        return accountRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }


    //delete only account
    @DeleteMapping("/delete")
    public String deleteAccount(@RequestParam Long accountId) {
        accountRepository.deleteById(accountId);
        return "Account deleted";
    }


}
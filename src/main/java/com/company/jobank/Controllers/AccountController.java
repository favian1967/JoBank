package com.company.jobank.Controllers;


import com.company.jobank.Entities.Account;
import com.company.jobank.Entities.User;
import com.company.jobank.Repositories.AccountRepository;
import com.company.jobank.Repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountController(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/balance")
    public Double checkBalance(
            @RequestParam Long userId
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));;
        Account account = accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance();
    }

    @PutMapping("/deposit")
    public String updateBalance(
            @RequestParam Long userId,
            @RequestParam Double amount
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));;
                Account account =  accountRepository.findByUserId(user.getId())
                        .orElseThrow(() -> new RuntimeException("Account not found"));
                account.setBalance(account.getBalance() + amount);
                accountRepository.save(account);
                return "Account updated successfully";
    }

    @PutMapping("/withdraw")
    public String withdraw(
            @RequestParam Long userId,
            @RequestParam Double amount
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));;
        Account account =  accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(account.getBalance() - amount);
        if (account.getBalance() < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        accountRepository.save(account);
        return "Account updated successfully";
    }


    @PutMapping("/transfer")
    public String transferMoney(
            @RequestParam Long transferFrom,
            @RequestParam Long transferTo,
            @RequestParam Double amount
    ){
        User user = userRepository.findById(transferFrom)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Account account =  accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));


        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        User  user2 = userRepository.findById(transferTo)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Account account1 = accountRepository.findByUserId(user2.getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account1.setBalance(account1.getBalance() + amount);
        accountRepository.save(account1);
        return "Account transfer successfully";
    }
}

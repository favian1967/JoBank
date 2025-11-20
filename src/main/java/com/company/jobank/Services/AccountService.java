package com.company.jobank.Services;


import com.company.jobank.Entities.Account;
import com.company.jobank.Entities.User;
import com.company.jobank.Repositories.AccountRepository;
import com.company.jobank.Repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }


    public Double getBalance(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));;
        Account account = accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance();
    }

    public Double deposit(Long userId, Double amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));;
        Account account =  accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(account.getBalance() + amount);

                accountRepository.save(account);
        return account.getBalance();

    }

    public String withdraw(Long userId, Double amount) {

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

    public String transfer(Long transferFrom, Long transferTo, Double amount) {
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

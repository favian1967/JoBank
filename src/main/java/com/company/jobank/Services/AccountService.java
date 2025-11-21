package com.company.jobank.Services;


import com.company.jobank.Entities.Account;
import com.company.jobank.Entities.Transaction;
import com.company.jobank.Entities.User;
import com.company.jobank.Enums.TransactionType;
import com.company.jobank.Repositories.AccountRepository;
import com.company.jobank.Repositories.TransactionRepository;
import com.company.jobank.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import static com.company.jobank.Enums.TransactionType.DEPOSIT;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }


    public Double getBalance(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));;
        Account account = accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance();
    }


    @Transactional
    public Double deposit(Long userId, Double amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));;
        Account account =  accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setBalance(account.getBalance() + amount);

        createTransaction(amount, account, "dep",  TransactionType.DEPOSIT, null, account);

        accountRepository.save(account);
        return account.getBalance();

    }
    @Transactional
    public String withdraw(Long userId, Double amount) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));;
        Account account =  accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        account.setBalance(account.getBalance() - amount);

        createTransaction(amount, account, "Withdraw",  TransactionType.WITHDRAW, account, null);

        accountRepository.save(account);
        return "Account updated successfully";
    }

    @Transactional
    public String transfer(Long transferFrom, Long transferTo, Double amount) {
        User user = userRepository.findById(transferFrom)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Account account =  accountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));


        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        if (transferFrom.equals(transferTo)) {
            throw new RuntimeException("Cannot transfer to the same account");
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        User  user2 = userRepository.findById(transferTo)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Account account1 = accountRepository.findByUserId(user2.getId())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        account1.setBalance(account1.getBalance() + amount);


        createTransaction(amount, account, "transfer",  TransactionType.TRANSFER, account, account1);


        accountRepository.save(account1);
        return "Account transfer successfully";
    }






    private void createTransaction(Double amount, Account account, String description, TransactionType type, Account fromAccount, Account toAccount) {
        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setDescription(description);

        transactionRepository.save(transaction);
    }

}
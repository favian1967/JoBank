package com.company.jobank.Controllers;

import com.company.jobank.Entities.Account;
import com.company.jobank.Entities.User;
import com.company.jobank.Repositories.AccountRepository;
import com.company.jobank.Repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public UserController(UserRepository userRepository, AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    @PostMapping("/add")
    public Long addUser(
            @RequestBody User user
    ){
        User savedUser = userRepository.save(user);
        Account account = new Account();
        account.setUser(savedUser);
        account.setBalance(0.0);
        accountRepository.save(account);
        return savedUser.getId();
    }
}
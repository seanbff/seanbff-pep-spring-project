package com.example.service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account getAccountById(int id) {
        return accountRepository.findById(id).orElse(null);
    }

    public Account addAccount(Account account){
        String username = account.getUsername();
        String password = account.getPassword();
        if (username.isEmpty()) throw new IllegalArgumentException("Username must not be empty");
        if (password.length() < 4) throw new IllegalArgumentException("Password must be at least 4 characters");
        if (accountRepository.findAccountByUsername(username) != null) throw new IllegalStateException("Username already exists");
        return accountRepository.save(account);
    }

    public Account loginAccount(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        Account existingAccount = accountRepository.findAccountByUsername(username);
        if (existingAccount == null) return null;
        if (!existingAccount.getPassword().equals(password)) return null;
        return existingAccount;
    };

}

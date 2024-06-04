package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;

@Service
public class AccountService {

    AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    /*
     * Process a new user registration.
     * 
     * @param username is not blank & does not already exist.
     * @param password is >4 characters.
     * @return a representation of the new account, not containin the accountId
     */
    public Account addAccount(Account account){
        if(account.getUsername() == "" || account.getPassword().length() < 4){
            return null;
        }

        Account newAccount = accountRepository.findAccountByUsername(account.getUsername());
        //check if account already exists
        if( newAccount != null){
            return null;
        }
        newAccount = accountRepository.save(account);
        //make sure new account was saved correctly
        if(newAccount != null){
            return newAccount;
        }
        return null;
    }

    /*
     * Return all accounts in the database.
     */
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    /*
     * Retrieve Account by accountId
     * 
     * @param accountId unique identifier for account
     * @return account assosciated with Id
     */
    public Account getAccountById(int accountId){
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        if(optionalAccount.isPresent()){
            return optionalAccount.get();
        }else{
            return null;
        }
    }


    /*
     * Retrieve Account by username
     * 
     * @param accountId unique identifier for account
     * @return account assosciated with Id
     */
    public Account getAccountByUsername(String username){
        Account optionalAccount = accountRepository.findAccountByUsername(username);
        if(optionalAccount!=null){
            return optionalAccount;
        }else{
            return null;
        }
    }

    /*
     * Save account to the database.
     * Provided the username and password on the account exist. 
     * 
     * @param account account to be saved
     * @return the saved account 
     */
    public Account saveAccount(Account account) {
        List<Account> accounts = accountRepository.findAll();
        Account logAccount = null;

        for(Account acc : accounts){
            if(acc.getUsername().equals(account.getUsername()) && acc.getPassword().equals(account.getPassword())){
                logAccount = acc;
            }
        }
        return logAccount;
    }
}

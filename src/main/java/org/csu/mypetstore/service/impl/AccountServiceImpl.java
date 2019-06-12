package org.csu.mypetstore.service.impl;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.persistence.AccountMapper;
import org.csu.mypetstore.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public Account getAccount(String username) {
        return accountMapper.getAccountByUsername(username);
    }

    @Override
    public Account getAccount(String username, String password) {
        Account account=new Account();
        account.setUsername(username);
        account.setPassword(password);
        return accountMapper.getAccountByUsernameAndPassword(account);
    }

    @Override
    public void insertAccount(Account account) {
        accountMapper.insertAccount(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountMapper.updateAccount(account);
    }

    @Override
    public boolean usernameIsExist(Account account) {
        if(accountMapper.getAccountByUsername(account.getUsername())!=null)
            return true;
        else return false;
    }
}

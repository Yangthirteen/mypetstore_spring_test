package org.csu.mypetstore.service;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.persistence.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface AccountService {

    public Account getAccount(String username);

    public Account getAccount(String username, String password);

    public void insertAccount(Account account);

    public void updateAccount(Account account);

    public boolean usernameIsExist(Account account);

}

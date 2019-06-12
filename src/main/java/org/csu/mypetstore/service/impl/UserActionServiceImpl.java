package org.csu.mypetstore.service.impl;

import org.csu.mypetstore.persistence.UserActionMapper;
import org.csu.mypetstore.service.UserActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserActionServiceImpl implements UserActionService {

    @Autowired
    private UserActionMapper userActionMapper;

    @Override
    public void record(String username, String action, String object, String date) {
        userActionMapper.recordAction(username,action,object,date);
    }
}

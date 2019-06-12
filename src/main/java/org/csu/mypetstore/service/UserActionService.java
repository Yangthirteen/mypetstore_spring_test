package org.csu.mypetstore.service;

public interface UserActionService {

    public void record(String username, String action, String object, String date);
}

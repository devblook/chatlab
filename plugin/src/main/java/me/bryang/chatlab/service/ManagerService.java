package me.bryang.chatlab.service;

import me.bryang.chatlab.manager.UserDataManager;

import javax.inject.Inject;

public class ManagerService implements Service{

    @Inject
    private UserDataManager userDataManager;

    @Override
    public void start() {
        userDataManager.init();
    }
}

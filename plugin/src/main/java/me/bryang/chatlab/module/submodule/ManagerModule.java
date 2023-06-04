package me.bryang.chatlab.module.submodule;

import me.bryang.chatlab.manager.MessageManager;
import me.bryang.chatlab.manager.UserDataManager;
import team.unnamed.inject.AbstractModule;

public class ManagerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MessageManager.class).singleton();

        bind(UserDataManager.class)
                .toInstance(new UserDataManager());
    }
}

package me.bryang.chatlab.module.submodule;

import me.bryang.chatlab.authorizer.Authorizer;
import me.bryang.chatlab.authorizer.tasks.IgnoreTask;
import team.unnamed.inject.AbstractModule;

public class AuthorizerModule extends AbstractModule{

    @Override
    protected void configure() {

        multibind(Authorizer.class)
                .asSet()
                .to(IgnoreTask.class);

    }
}

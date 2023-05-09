package me.bryang.chatlab.module;

import me.bryang.chatlab.service.ListenerServices;
import me.bryang.chatlab.service.Service;
import me.bryang.chatlab.service.translator.CommandServices;
import team.unnamed.inject.AbstractModule;

public class ServiceModule extends AbstractModule {
    @Override
    public void configure() {
        multibind(Service.class).asSet()
                .to(CommandServices.class)
                .to(ListenerServices.class);
    }
}

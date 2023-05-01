package me.bryang.chatlab.modules;

import me.bryang.chatlab.services.ListenerServices;
import me.bryang.chatlab.services.Service;
import me.bryang.chatlab.services.translator.CommandServices;
import team.unnamed.inject.AbstractModule;

public class ServiceModule extends AbstractModule {
    @Override
    public void configure() {
        multibind(Service.class).asSet()
                .to(CommandServices.class)
                .to(ListenerServices.class);
    }
}

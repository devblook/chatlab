package me.bryang.chatlab.module.submodule;

import me.bryang.chatlab.service.ListenerService;
import me.bryang.chatlab.service.Service;
import me.bryang.chatlab.service.translator.CommandService;
import team.unnamed.inject.AbstractModule;

public class ServiceModule extends AbstractModule {

    @Override
    public void configure() {
        multibind(Service.class).asSet()
                .to(CommandService.class)
                .to(ListenerService.class);
    }
}

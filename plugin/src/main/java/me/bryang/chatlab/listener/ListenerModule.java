package me.bryang.chatlab.listener;

import org.bukkit.event.Listener;
import team.unnamed.inject.AbstractModule;

public class ListenerModule extends AbstractModule {

    @Override
    public void configure() {
        multibind(Listener.class).asSet()
                .to(PlayerRegistryListener.class);
    }
}

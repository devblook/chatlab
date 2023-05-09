package me.bryang.chatlab.module;

import me.bryang.chatlab.listener.PlayerRegistryListener;
import org.bukkit.event.Listener;
import team.unnamed.inject.AbstractModule;

public class ListenerModule extends AbstractModule {

    @Override
    public void configure() {

        multibind(Listener.class)
                .asSet()
                .to(PlayerRegistryListener.class)
                .singleton();
    }
}

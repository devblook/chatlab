package me.bryang.chatlab.service;

import me.bryang.chatlab.ChatLab;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import team.unnamed.inject.InjectAll;

import java.util.Set;

@InjectAll
public class ListenerService implements Service {

    private ChatLab plugin;
    private Set<Listener> listeners;

    @Override
    public void start() {
        PluginManager pluginManager = plugin.getServer().getPluginManager();

        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, plugin);
        }
    }
}

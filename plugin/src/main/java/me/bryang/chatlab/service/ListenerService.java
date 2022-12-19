package me.bryang.chatlab.service;

import me.bryang.chatlab.ChatLab;
import me.bryang.chatlab.api.Service;
import me.bryang.chatlab.listener.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import javax.inject.Inject;

public class ListenerService implements Service {

    @Inject
    private ChatLab chatLab;

    @Override
    public void start() {

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerQuitListener(), chatLab);
    }
}

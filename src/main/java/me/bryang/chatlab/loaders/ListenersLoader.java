package me.bryang.chatlab.loaders;

import me.bryang.chatlab.ChatLab;
import me.bryang.chatlab.Loader;
import me.bryang.chatlab.listeners.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import javax.inject.Inject;

public class ListenersLoader implements Loader {

    @Inject
    private ChatLab chatLab;

    @Override
    public void load() {

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerQuitListener(), chatLab);
    }
}

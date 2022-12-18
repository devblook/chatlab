package me.bryang.chatlab.loaders;

import me.bryang.chatlab.Loader;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenersLoader implements Loader {

    @Override
    public void load() {
        PluginManager pl = Bukkit.getPluginManager();
    }
}

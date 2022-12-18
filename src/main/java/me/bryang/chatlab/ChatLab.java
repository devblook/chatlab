package me.bryang.chatlab;

import me.bryang.chatlab.modules.PluginModule;
import org.bukkit.plugin.java.JavaPlugin;
import team.unnamed.inject.Injector;

import javax.inject.Inject;

public class ChatLab extends JavaPlugin {

    @Inject
    private PluginCore pluginCore;

    @Override
    public void onEnable() {

        Injector injector = Injector.create(new PluginModule(this));
        injector.injectMembers(this);

        pluginCore.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

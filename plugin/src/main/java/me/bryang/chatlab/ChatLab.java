package me.bryang.chatlab;

import me.bryang.chatlab.services.Service;
import org.bukkit.plugin.java.JavaPlugin;
import team.unnamed.inject.Injector;

import javax.inject.Inject;
import java.util.Set;

public class ChatLab extends JavaPlugin {

    @Inject
    private Set<Service> services;

    @Override
    public void onLoad() {
        Injector injector = Injector.create(new PluginModule(this));
        injector.injectMembers(this);
    }

    @Override
    public void onEnable() {
        services.forEach(Service::start);
    }

    @Override
    public void onDisable() {
    }
}

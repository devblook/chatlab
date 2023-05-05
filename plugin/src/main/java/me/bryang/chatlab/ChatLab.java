package me.bryang.chatlab;

import me.bryang.chatlab.modules.MainModule;
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

        Injector injector = Injector.create(new MainModule(this));
        injector.injectMembers(this);
    }

    @Override
    public void onEnable() {

        services.forEach(Service::start);

        getLogger().info("Loaded services");
        getLogger().info("Thanks for using my plugin!");
    }

}

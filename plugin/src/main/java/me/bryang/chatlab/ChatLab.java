package me.bryang.chatlab;

import me.bryang.chatlab.module.MainModule;
import me.bryang.chatlab.service.Service;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import team.unnamed.inject.Injector;

import javax.inject.Inject;
import java.util.Set;

public class ChatLab extends JavaPlugin {

    @Inject
    private Set<Service> services;

    @Inject
    private Logger logger;

    @Override
    public void onLoad() {
        Injector.create(new MainModule(this))
                .injectMembers(this);
    }

    @Override
    public void onEnable() {
        this.services.forEach(Service::start);

        this.logger.info("Loaded services");
        this.logger.info("Thanks for using my plugin!");
    }

    @Override
    public void onDisable() {
        this.services.forEach(Service::stop);
    }
}

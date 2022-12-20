package me.bryang.chatlab;

import me.bryang.chatlab.api.Service;
import me.bryang.chatlab.manager.FileManager;
import me.bryang.chatlab.service.CommandService;
import me.bryang.chatlab.service.ListenerService;
import me.bryang.chatlab.user.UserManager;
import team.unnamed.inject.AbstractModule;

public class PluginModule extends AbstractModule {

    private final ChatLab plugin;

    public PluginModule(ChatLab plugin){
        this.plugin = plugin;
    }

    @Override
    public void configure() {
        bind(ChatLab.class).toInstance(plugin);

        multibind(Service.class).asSet()
                .to(CommandService.class)
                .to(ListenerService.class);

        bind(FileManager.class)
                .toInstance(new FileManager(plugin, "config"));
        bind(FileManager.class)
                .named("messages")
                .toInstance(new FileManager(plugin, "messages"));

        bind(UserManager.class).toInstance(new UserManager());

    }
}

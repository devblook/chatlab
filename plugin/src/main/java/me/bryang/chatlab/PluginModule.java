package me.bryang.chatlab;

import me.bryang.chatlab.api.Service;
import me.bryang.chatlab.api.utils.TypeRegistry;
import me.bryang.chatlab.api.utils.TypeRegistryImpl;
import me.bryang.chatlab.command.CommandModule;
import me.bryang.chatlab.listener.ListenerModule;
import me.bryang.chatlab.manager.BukkitFileManager;
import me.bryang.chatlab.command.CommandService;
import me.bryang.chatlab.listener.ListenerService;
import me.bryang.chatlab.user.User;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.key.TypeReference;

public class PluginModule extends AbstractModule {

    private final ChatLab plugin;

    public PluginModule(ChatLab plugin){
        this.plugin = plugin;
    }

    @Override
    public void configure() {
        bind(ChatLab.class)
                .toInstance(plugin);

        multibind(Service.class).asSet()
                .to(CommandService.class)
                .to(ListenerService.class);

        bind(BukkitFileManager.class)
                .toInstance(new BukkitFileManager(plugin, "config"));
        bind(BukkitFileManager.class)
                .named("messages")
                .toInstance(new BukkitFileManager(plugin, "messages"));

        bind(new TypeReference<TypeRegistry<User>>(){})
                .toInstance(new TypeRegistryImpl<>());


        install(new ListenerModule());
        install(new CommandModule());
    }
}

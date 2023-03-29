package me.bryang.chatlab;

import me.bryang.chatlab.api.Service;
import me.bryang.chatlab.api.utils.TypeRegistry;
import me.bryang.chatlab.api.utils.TypeRegistryImpl;
import me.bryang.chatlab.commands.CommandService;
import me.bryang.chatlab.listeners.ListenerService;
import me.bryang.chatlab.manager.SenderManager;
import me.bryang.chatlab.manager.YamlFileManager;
import me.bryang.chatlab.modules.CommandModule;
import me.bryang.chatlab.modules.ListenerModule;
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

        bind(YamlFileManager.class)
                .toInstance(new YamlFileManager(plugin, "config"));
        bind(YamlFileManager.class)
                .named("messages")
                .toInstance(new YamlFileManager(plugin, "messages"));

        bind(new TypeReference<TypeRegistry<User>>(){})
                .toInstance(new TypeRegistryImpl<>());

        bind(SenderManager.class).toInstance(new SenderManager(plugin));
        install(new ListenerModule());
        install(new CommandModule());
    }
}

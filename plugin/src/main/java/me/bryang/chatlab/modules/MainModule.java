package me.bryang.chatlab.modules;

import me.bryang.chatlab.ChatLab;
import me.bryang.chatlab.FileCreator;
import me.bryang.chatlab.manager.SenderManager;
import me.bryang.chatlab.modules.CommandModule;
import me.bryang.chatlab.modules.ListenerModule;
import me.bryang.chatlab.services.ListenerServices;
import me.bryang.chatlab.services.Service;
import me.bryang.chatlab.services.translator.CommandCustomTranslator;
import me.bryang.chatlab.services.translator.CommandServices;
import me.bryang.chatlab.user.User;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.key.TypeReference;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class MainModule extends AbstractModule {

    private final ChatLab plugin;
    private final Path pluginPath;

    public MainModule(ChatLab plugin){
        this.plugin = plugin;
        this.pluginPath = plugin.getDataFolder().toPath();
    }

    @Override
    public void configure() {
        bind(ChatLab.class)
                .toInstance(plugin);

        multibind(Service.class).asSet()
                .to(CommandServices.class)
                .to(ListenerServices.class);

        bind(CommandCustomTranslator.class)
                .toInstance(new CommandCustomTranslator());

        bind(FileCreator.class)
                .toInstance(new FileCreator(pluginPath, "config"));

        bind(FileCreator.class)
                .named("messages")
                .toInstance(new FileCreator(pluginPath, "messages"));

        bind(new TypeReference<Map<String, User>>(){})
                .toInstance(new HashMap<>());

        bind(SenderManager.class)
                .toInstance(new SenderManager());

        install(new ListenerModule());
        install(new CommandModule());
    }
}

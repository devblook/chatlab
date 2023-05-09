package me.bryang.chatlab.module;

import me.bryang.chatlab.ChatLab;
import me.bryang.chatlab.file.FileWrapper;
import me.bryang.chatlab.file.type.ConfigurationFile;
import me.bryang.chatlab.file.type.MessagesFile;
import me.bryang.chatlab.manager.SenderManager;
import me.bryang.chatlab.service.translator.CommandCustomTranslator;
import me.bryang.chatlab.user.User;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.key.TypeReference;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class MainModule extends AbstractModule {

    private final ChatLab plugin;
    private final Path pluginPath;

    public MainModule(ChatLab plugin) {
        this.plugin = plugin;
        this.pluginPath = plugin.getDataFolder().toPath();
    }

    @Override
    public void configure() {
        bind(ChatLab.class)
                .toInstance(plugin);

        bind(new TypeReference<FileWrapper<ConfigurationFile>>(){})
                .toInstance(new FileWrapper<>
                        ("config.yml", pluginPath, ConfigurationFile.class, new ConfigurationFile()));

        bind(new TypeReference<FileWrapper<MessagesFile>>(){})
                .toInstance(new FileWrapper<>
                        ("messages.yml", pluginPath, MessagesFile.class, new MessagesFile()));

        bind(CommandCustomTranslator.class)
                .toInstance(new CommandCustomTranslator());



        bind(new TypeReference<Map<String, User>>() {})
                .toInstance(new HashMap<>());

        bind(SenderManager.class)
                .toInstance(new SenderManager());

        install(new ListenerModule());
        install(new CommandModule());
        install(new ServiceModule());
    }
}

package me.bryang.chatlab.module;

import me.bryang.chatlab.ChatLab;
import me.bryang.chatlab.module.submodule.CommandModule;
import me.bryang.chatlab.module.submodule.ConfigurationModule;
import me.bryang.chatlab.module.submodule.ListenerModule;
import me.bryang.chatlab.module.submodule.ServiceModule;
import me.bryang.chatlab.user.User;
import org.slf4j.Logger;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.Provides;
import team.unnamed.inject.key.TypeReference;

import javax.inject.Singleton;
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

    @Provides
    @Singleton
    private Logger provideLogger(ChatLab plugin) {
        return plugin.getSLF4JLogger();
    }

    @Override
    public void configure() {
        super.bind(ChatLab.class)
                .toInstance(plugin);

        super.bind(Path.class)
                .named("PluginFolder")
                .toInstance(pluginPath);

        super.bind(new TypeReference<Map<String, User>>() {})
                .toInstance(new HashMap<>());

        super.install(new ListenerModule());
        super.install(new CommandModule());
        super.install(new ServiceModule());
        super.install(new ConfigurationModule());
    }
}

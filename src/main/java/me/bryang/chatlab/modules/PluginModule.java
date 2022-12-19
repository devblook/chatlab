package me.bryang.chatlab.modules;

import me.bryang.chatlab.ChatLab;
import me.bryang.chatlab.Loader;
import me.bryang.chatlab.PluginCore;
import me.bryang.chatlab.loaders.CommandLoader;
import me.bryang.chatlab.loaders.ListenersLoader;
import team.unnamed.inject.Binder;
import team.unnamed.inject.Module;

public class PluginModule implements Module {

    private final ChatLab plugin;

    public PluginModule(ChatLab plugin){
        this.plugin = plugin;
    }

    @Override
    public void configure(Binder binder) {

        binder.bind(CommandLoader.class).toInstance(new CommandLoader());
        binder.bind(ListenersLoader.class).toInstance(new ListenersLoader());

        binder.bind(Loader.class).to(ListenersLoader.class);
        binder.bind(Loader.class).to(CommandLoader.class);

        binder.bind(PluginCore.class).toInstance(new PluginCore());

        binder.install(new FileModule(plugin));

    }
}

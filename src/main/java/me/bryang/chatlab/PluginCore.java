package me.bryang.chatlab;

import me.bryang.chatlab.loaders.CommandLoader;
import me.bryang.chatlab.loaders.ListenersLoader;
import team.unnamed.inject.InjectAll;

@InjectAll
public class PluginCore {

    @Named("command-listener")
    private CommandLoader commandLoader;

    @Named("listeners-loader")
    private ListenersLoader listenersLoader;

    public void init(){

        initLoaders(commandLoader, listenersLoader);
    }

    void initLoaders(Loader... loaders){
        for (Loader loader : loaders){
            loader.load();
        }
    }
}

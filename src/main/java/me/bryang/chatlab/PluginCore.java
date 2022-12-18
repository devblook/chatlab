package me.bryang.chatlab;

import me.bryang.chatlab.loaders.CommandLoader;
import me.bryang.chatlab.loaders.ListenersLoader;
import team.unnamed.inject.InjectAll;

import javax.inject.Inject;

@InjectAll

public class PluginCore {

    private CommandLoader commandLoader;
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

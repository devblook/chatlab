package me.bryang.chatlab.modules;

import me.bryang.chatlab.ChatLab;
import me.bryang.chatlab.managers.FileManager;
import team.unnamed.inject.Binder;
import team.unnamed.inject.Module;

public class FileModule implements Module {

    private final ChatLab plugin;

    public FileModule(ChatLab plugin){
        this.plugin = plugin;
    }

    @Override
    public void configure(Binder binder) {
        binder.bind(FileManager.class).named("config").toInstance(new FileManager(plugin, "config"));
        binder.bind(FileManager.class).named("messages").toInstance(new FileManager(plugin, "messages"));

    }
}

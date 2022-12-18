package me.bryang.chatlab.loaders.translator;

import me.bryang.chatlab.managers.FileManager;
import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.translator.TranslationProvider;

public class CommandTranslator implements TranslationProvider {

    private final FileManager messagesFile;

    public CommandTranslator(FileManager messagesFile){
        this.messagesFile = messagesFile;
    }

    @Override
    public String getTranslation(Namespace namespace, String key) {

        switch (key) {
            case "sender.only-player":
                return messagesFile.getString("error.no-console");

            case "player.offline":
                return messagesFile.getString("error.no-online");

            case "command.no-permission":
                 return messagesFile.getString("error.no-permission");

        }
        return "Si ves este mensaje, contacta con el programador.";
    }

}

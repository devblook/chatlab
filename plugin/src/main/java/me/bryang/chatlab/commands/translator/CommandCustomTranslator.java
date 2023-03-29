package me.bryang.chatlab.commands.translator;

import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.translator.TranslationProvider;
import org.bukkit.configuration.file.FileConfiguration;

public class CommandCustomTranslator implements TranslationProvider {

    private final FileConfiguration messagesFile;

    public CommandCustomTranslator(FileConfiguration messagesFile){
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

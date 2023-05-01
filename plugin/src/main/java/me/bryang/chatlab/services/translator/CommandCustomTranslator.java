package me.bryang.chatlab.services.translator;

import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.translator.TranslationProvider;
import org.bukkit.configuration.file.FileConfiguration;

import javax.inject.Inject;

public class CommandCustomTranslator implements TranslationProvider {

    @Inject
    private FileConfiguration messagesFile;

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

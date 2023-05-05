package me.bryang.chatlab.services.translator;

import me.bryang.chatlab.file.FileWrapper;
import me.bryang.chatlab.file.types.ConfigurationFile;
import me.bryang.chatlab.file.types.MessagesFile;
import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.translator.TranslationProvider;

import javax.inject.Inject;
import javax.inject.Named;

public class CommandCustomTranslator implements TranslationProvider {

    private FileWrapper<MessagesFile> messagesFile;

    @Override
    public String getTranslation(Namespace namespace, String key) {

        MessagesFile messagesPath = messagesFile.get();

        switch (key) {
            case "sender.only-player":
                return messagesPath.consoleMessage();

            case "player.offline":
                return messagesPath.playerOfflineMessage();

            case "command.no-permission":
                return messagesPath.noPermissionMessage();

        }
        return "Si ves este mensaje, contacta con el programador.";
    }

}

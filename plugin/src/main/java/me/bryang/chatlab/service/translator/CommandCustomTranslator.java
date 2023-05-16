package me.bryang.chatlab.service.translator;

import me.bryang.chatlab.file.FileWrapper;
import me.bryang.chatlab.file.type.MessagesFile;
import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.translator.TranslationProvider;

import javax.inject.Inject;

public class CommandCustomTranslator implements TranslationProvider {

    @Inject
    private FileWrapper<MessagesFile>  messagesFile;
    @Override
    public String getTranslation(Namespace namespace, String key) {

        MessagesFile messagesPath = messagesFile.get();

        switch (key) {
            case "sender.only-player":
                return messagesPath.consoleMessage();

        }
        return "Si ves este mensaje, contacta con el programador.";
    }

}

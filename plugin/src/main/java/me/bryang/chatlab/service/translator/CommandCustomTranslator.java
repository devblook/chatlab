package me.bryang.chatlab.service.translator;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.MessageSection;
import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.translator.TranslationProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommandCustomTranslator implements TranslationProvider {

    @Inject
    private ConfigurationContainer<MessageSection> messageContainer;

    @Override
    public String getTranslation(Namespace namespace, String key) {
        MessageSection messageSection = messageContainer.get();

        if (key.equals("sender.only-player")) {
            return messageSection.error.console;
        }
        return "If u see this message, please contact with us.";
    }
}

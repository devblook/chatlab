package me.bryang.chatlab.service.command;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.MessageSection;
import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.translator.TranslationProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CommandCustomTranslator implements TranslationProvider {

    @Inject
    private ConfigurationContainer<MessageSection> messageContainer;

    @Inject
    private Logger logger;


    @Override
    public String getTranslation(Namespace namespace, String path){
        MessageSection messageSection = messageContainer.get();

        String key = path.split("\\|")[0].trim();

        String text = switch (key) {
            case "sender.only_player" -> messageSection.error.console;
            case "command.subcommand.invalid" -> messageSection.error.noArgument
                    .replace("<usage>", path.split("\\|")[1]);
            case "player.offline" -> messageSection.error.playerOffline
                    .replace("<player>", "%s");
            default -> "If u see this message, please contact with us.";
        };


        return ChatColor.translateAlternateColorCodes('&', LegacyComponentSerializer.legacyAmpersand()
                .serialize(MiniMessage.miniMessage().deserialize(text)));


    }
}
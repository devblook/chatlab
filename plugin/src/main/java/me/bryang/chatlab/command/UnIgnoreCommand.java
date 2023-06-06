package me.bryang.chatlab.command;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.MessageSection;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.message.MessageManager;
import me.bryang.chatlab.user.User;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Map;

@Command(names = "unignore")

public class UnIgnoreCommand implements CommandClass {

    private MessageManager messageManager;
    private ConfigurationContainer<RootSection> configurationContainer;
    private ConfigurationContainer<MessageSection> messageContainer;
    private Map<String, User> userData;

    public void unIgnoreCommand(@Sender Player sender, OfflinePlayer target){

        RootSection rootSection = configurationContainer.get();
        MessageSection messageSection = messageContainer.get();

        if (sender.getUniqueId() == target.getUniqueId()){
            messageManager.sendMessage(sender, messageSection.error.yourselfIgnore);
            return;
        }

        User user = userData.get(target.getUniqueId().toString());

        if (user.containsIgnoredPlayers(target.getUniqueId())){
            messageManager.sendMessage(sender, messageSection.error.playerAlreadyUnIgnored,
                    Placeholder.unparsed("player", target.getName()));
            return;
        }

        user.unIgnore(target.getUniqueId());
        messageManager.sendMessage(sender, rootSection.ignore.unignoredPlayer,
                Placeholder.unparsed("player", target.getName()));

    }
}

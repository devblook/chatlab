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
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import java.util.Map;

@Command(names = "ignore")
@InjectAll
public class IgnoreCommand implements CommandClass {

    private MessageManager messageManager;
    private ConfigurationContainer<RootSection> configurationContainer;
    private ConfigurationContainer<MessageSection> messageContainer;
    private Map<String, User> userData;


    @Command(names = "")
    public void ignoreCommand(@Sender Player sender, Player target){

        RootSection rootSection = configurationContainer.get();
        MessageSection messageSection = messageContainer.get();

        if (sender.getUniqueId() == target.getUniqueId()){
            messageManager.sendMessage(sender, messageSection.error.yourselfIgnore);
            return;
        }

        User user = userData.get(target.getUniqueId().toString());

        if (user.containsIgnoredPlayers(target.getUniqueId())){
            messageManager.sendMessage(sender, messageSection.error.playerAlreadyIgnored,
                    Placeholder.unparsed("player", target.getName()));
            return;
        }

        user.ignore(target.getUniqueId());
        messageManager.sendMessage(sender, rootSection.ignore.ignoredPlayer,
                Placeholder.unparsed("player", target.getName()));

    }
}

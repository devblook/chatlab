package me.bryang.chatlab.command;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.configuration.section.MessageSection;
import me.bryang.chatlab.manager.MessageManager;
import me.bryang.chatlab.user.User;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import java.util.Map;

@InjectAll
public class ReplyCommand implements CommandClass {

    private ConfigurationContainer<RootSection> configurationContainer;
    private ConfigurationContainer<MessageSection> messageContainer;
    private Map<String, User> users;
    private MessageManager messageManager;


    @Command(names = {"r", "reply"},
            desc = "A reply command.")
    public void messageCommand(@Sender Player sender, @Text @OptArg() String senderMessage) {

        MessageSection messageSection = this.messageContainer.get();
        RootSection configFile = this.configurationContainer.get();

        if (senderMessage.isEmpty()) {
            this.messageManager.sendMessage(sender, messageSection.error.noArgument,

                    Placeholder.unparsed("usage", "/reply <message>"));
            return;
        }

        User user = this.users.get(sender.getUniqueId().toString());

        if (!user.hasRecentMessenger()) {
            this.messageManager.sendMessage(sender, messageSection.error.noReply);
            return;
        }

        Player target = Bukkit.getPlayer(user.recentMessenger());

        if (target == null) {
            this.messageManager.sendMessage(sender, messageSection.error.noReply);
            return;
        }

        this.messageManager.sendMessage(sender, configFile.privateMessage.fromSender,

                Placeholder.unparsed("target", target.getName()),
                Placeholder.unparsed("message", senderMessage));

        this.messageManager.sendMessage(target, configFile.privateMessage.toReceptor,

                Placeholder.unparsed("sender", sender.getName()),
                Placeholder.unparsed("message", senderMessage));

    }

}

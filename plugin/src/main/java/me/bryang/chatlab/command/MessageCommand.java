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
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import java.util.Map;

//Todo 16-05-2023: To separate the logic of private messages in a separate class for better distribution
@InjectAll
public class MessageCommand implements CommandClass {

    private ConfigurationContainer<RootSection> configurationContainer;
    private ConfigurationContainer<MessageSection> messageContainer;
    private Map<String, User> users;
    private MessageManager messageManager;

    @Command(names = {"msg", "pm", "m", "message", "tell", "w"},
            desc = "Command to send a private message.")
    public void messageCommand(@Sender Player sender, @OptArg() OfflinePlayer target,
                               @Text @OptArg("") String senderMessage) {

        RootSection configFile = this.configurationContainer.get();
        MessageSection messageSection = this.messageContainer.get();

        if (target == null) {
            this.messageManager.sendMessage(sender, messageSection.error.noArgument,
                    Placeholder.unparsed("usage", "/msg <player> <message>"));
            return;
        }

        if (sender == target.getPlayer()) {
            this.messageManager.sendMessage(sender, messageSection.error.yourselfTalk);
            return;
        }

        if (!target.isOnline()) {
            this.messageManager.sendMessage(sender, messageSection.error.playerOffline,
                    Placeholder.unparsed("usage", "/msg <player> <message>"));
            return;
        }


        if (senderMessage.isEmpty()) {
            this.messageManager.sendMessage(sender, messageSection.error.noArgument,
                    Placeholder.unparsed("usage", "/msg <player> <message>"));
            return;
        }

        this.messageManager.sendMessage(sender, configFile.privateMessage.fromSender,
                Placeholder.unparsed("target", target.getName()),
                Placeholder.unparsed("message", senderMessage));

        messageManager.sendMessage(target.getPlayer(), configFile.privateMessage.toReceptor,
                Placeholder.unparsed("sender", sender.getName()),
                Placeholder.unparsed("message", senderMessage));

        User senderUser = this.users.get(sender.getUniqueId().toString());
        User senderTarget = this.users.get(target.getUniqueId().toString());

        senderUser.recentMessenger(target.getUniqueId());
        senderTarget.recentMessenger(sender.getUniqueId());
    }
}

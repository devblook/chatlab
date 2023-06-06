package me.bryang.chatlab.command;

import me.bryang.chatlab.message.authorizer.MessageAuthorizer;
import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.MessageSection;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.message.MessageManager;
import me.bryang.chatlab.user.User;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import java.util.Map;

//Todo 16-05-2023: To separate the logic of private messages in a separate class for better distribution
@InjectAll
public class MessageCommand implements CommandClass {

    private ConfigurationContainer<RootSection> configurationContainer;
    private ConfigurationContainer<MessageSection> messageContainer;
    private Map<String, User> userData;
    private MessageManager messageManager;
    private MessageAuthorizer messageAuthorizer;

    @Command(names = {"msg", "pm", "m", "message", "tell", "w"},
            desc = "Command to send a private message.")
    public void messageCommand(@Sender Player sender, Player target,
                               @Text String senderMessage) {

        RootSection configFile = configurationContainer.get();
        MessageSection messageSection = messageContainer.get();


        if (sender == target.getPlayer()) {
            messageManager.sendMessage(sender, messageSection.error.yourselfTalk);
            return;
        }

        if (messageAuthorizer.isAuthorized(sender.getUniqueId().toString(), target.getUniqueId().toString())){
            messageManager.sendMessage(sender, messageSection.error.noArgument,
                    Placeholder.unparsed("usage", "/msg <player> <message>"));
            return;
        }

        messageManager.sendMessage(sender, configFile.privateMessage.fromSender,
                Placeholder.unparsed("target", target.getName()),
                Placeholder.unparsed("message", senderMessage));

        messageManager.sendMessage(target, configFile.privateMessage.toReceptor,
                Placeholder.unparsed("sender", sender.getName()),
                Placeholder.unparsed("message", senderMessage));

        User senderUser = userData.get(sender.getUniqueId().toString());
        User senderTarget = userData.get(target.getUniqueId().toString());

        senderUser.recentMessenger(target.getUniqueId());
        senderTarget.recentMessenger(sender.getUniqueId());
    }
}

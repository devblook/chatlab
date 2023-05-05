package me.bryang.chatlab.commands;

import me.bryang.chatlab.file.FileWrapper;
import me.bryang.chatlab.file.types.ConfigurationFile;
import me.bryang.chatlab.file.types.MessagesFile;
import me.bryang.chatlab.manager.SenderManager;
import me.bryang.chatlab.user.User;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;
import team.unnamed.inject.InjectIgnore;

import javax.inject.Named;
import java.io.ObjectInputFilter;
import java.util.Map;

@InjectAll
public class ReplyCommand implements CommandClass {

    private FileWrapper<ConfigurationFile> configWrapper;
    private FileWrapper<MessagesFile> messagesWrapper;

    private Map<String, User> users;
    private SenderManager senderManager;

    @InjectIgnore
    private final ConfigurationFile configFile = configWrapper.get();
    @InjectIgnore
    private final MessagesFile messagesFile = messagesWrapper.get();

    @Command(names = {"r", "reply"},
            desc = "A reply command.")
    public void messageCommand(@Sender Player sender, @Text @OptArg() String senderMessage) {

        if (senderMessage.isEmpty()) {
            sender.sendMessage(messagesFile.noArgumentMessage()
                    .replace("%usage%", "/msg <player> <message>"));
            return;
        }

        User user = users.get(sender.getUniqueId().toString());

        if (!user.hasRecentMessenger()) {
            senderManager.sendMessage(sender, messagesFile.replyMessage());
            return;
        }

        Player target = Bukkit.getPlayer(user.recentMessenger());

        if (target == null) {
            senderManager.sendMessage(sender, messagesFile.replyMessage());
            return;
        }

        senderManager.sendMessage(sender, configFile.fromSenderMessage()
                .replace("%target%", target.getName())
                .replace("%message%", senderMessage));

        senderManager.sendMessage(target, configFile.toReceptorMessage()
                .replace("%sender%", sender.getName())
                .replace("%message%", senderMessage));
    }

}

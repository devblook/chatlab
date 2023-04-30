package me.bryang.chatlab.commands;

import me.bryang.chatlab.manager.SenderManager;
import me.bryang.chatlab.FileCreator;
import me.bryang.chatlab.user.User;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.Map;

@InjectAll
public class ReplyCommand implements CommandClass {

    private FileCreator configFile;
    @Named("messages")
    private FileCreator messagesFile;

    private Map<String, User> users;
    private SenderManager senderManager;

    @Command(names = {"r", "reply"},
            desc = "A reply command.")
    public void messageCommand(@Sender Player sender, @Text @OptArg() String senderMessage) {

        if (senderMessage.isEmpty()){
            sender.sendMessage(messagesFile.getString("error.no-argument")
                    .replace("%usage%", "/msg <player> <message>"));
            return;
        }

        User user = users.get(sender.getUniqueId().toString());

        if (!user.hasRecentMessenger()) {
            senderManager.sendMessage(sender, messagesFile.getString("error.no-reply"));
            return;
        }

        Player target = Bukkit.getPlayer(user.recentMessenger());

        if (target == null) {
            senderManager.sendMessage(sender, messagesFile.getString("error.no-reply"));
            return;
        }

        senderManager.sendMessage(sender, configFile.getString("private-messages.from-sender")
                .replace("%target%", target.getName())
                .replace("%message%", senderMessage));

        senderManager.sendMessage(target, configFile.getString("private-messages.to-receptor")
                .replace("%sender%", sender.getName())
                .replace("%message%", senderMessage));
    }

}

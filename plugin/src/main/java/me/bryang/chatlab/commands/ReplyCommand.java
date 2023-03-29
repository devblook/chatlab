package me.bryang.chatlab.commands;

import me.bryang.chatlab.api.utils.TypeRegistry;
import me.bryang.chatlab.manager.SenderManager;
import me.bryang.chatlab.manager.YamlFileManager;
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

@InjectAll
public class ReplyCommand implements CommandClass {

    private YamlFileManager configFile;
    @Named("messages")
    private YamlFileManager messagesFile;

    private TypeRegistry<User> users;
    private SenderManager senderManager;

    @Command(names = {"r", "reply"},
            desc = "A reply command.")
    public void messageCommand(@Sender Player sender, @Text @OptArg() String senderMessage) {

        FileConfiguration config = configFile.get();
        FileConfiguration messages = messagesFile.get();

        if (senderMessage.isEmpty()){
            sender.sendMessage(messages.getString("error.no-argument")
                    .replace("%usage%", "/msg <player> <message>"));
            return;
        }

        User user = users.get(sender.getUniqueId().toString());

        if (!user.hasRecentMessenger()) {
            senderManager.sendMessage(sender, messages.getString("error.no-reply"));
            return;
        }

        Player target = Bukkit.getPlayer(user.recentMessenger());

        if (target == null) {
            senderManager.sendMessage(sender, messages.getString("error.no-reply"));
            return;
        }

        senderManager.sendMessage(sender, config.getString("private-messages.from-sender")
                .replace("%target%", target.getName())
                .replace("%message%", senderMessage));

        senderManager.sendMessage(target, config.getString("private-messages.to-receptor")
                .replace("%sender%", sender.getName())
                .replace("%message%", senderMessage));
    }

}

package me.bryang.chatlab.commands;

import me.bryang.chatlab.manager.SenderManager;
import me.bryang.chatlab.FileCreator;
import me.bryang.chatlab.user.User;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.Map;

@InjectAll
public class MessageCommand implements CommandClass {

    private FileCreator configFile;

    @Named("messages")
    private FileCreator messagesFile;

    private Map<String, User> users;
    private SenderManager senderManager;

    @Command(names = {"msg", "pm", "m", "message", "tell", "w"},
            desc = "Private message command")
    public void messageCommand(@Sender Player sender, @OptArg() Player target,
                               @Text @OptArg() String senderMessage){

        FileConfiguration config = configFile.get();
        FileConfiguration messages = messagesFile.get();

        if (target == null){
            senderManager.sendMessage(sender, messages.getString("error.no-argument")
                    .replace("%usage%", "/msg <player> <message>"));
            return;
        }

        if (senderMessage.isEmpty()){
            senderManager.sendMessage(sender,messages.getString("error.no-argument")
                    .replace("%usage%", "/msg <player> <message>"));
            return;
        }

        senderManager.sendMessage(sender, messages.getString("private-messages.from-sender")
                .replace("%target%", target.getName())
                .replace("%message%", senderMessage));

        senderManager.sendMessage(target, config.getString("private-messages.to-receptor")
                .replace("%sender%", sender.getName())
                .replace("%message%", senderMessage));

        User senderUser = users.get(sender.getUniqueId().toString());
        User senderTarget = users.get(sender.getUniqueId().toString());

        senderUser.recentMessenger(target.getUniqueId());
        senderTarget.recentMessenger(sender.getUniqueId());
    }
}

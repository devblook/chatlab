package me.bryang.chatlab.command;

import me.bryang.chatlab.ChatLab;
import me.bryang.chatlab.manager.FileManager;
import me.bryang.chatlab.user.User;
import me.bryang.chatlab.user.UserManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import java.util.UUID;

@InjectAll
@Command(names = "r, reply", desc = "A reply command.")
public class ReplyCommand implements CommandClass {

    @Named("config")
    private FileManager configFile;

    @Named("messages")
    private FileManager messagesFile;

    private UserManager userManager;

    public void messageCommand(

            @Sender Player sender,
            @Text @OptArg("") String senderMessage) {


        if (senderMessage.isEmpty()){
            sender.sendMessage(messagesFile.getString("no-argument")
                    .replace("%usage%", "/msg <player> <message>"));
            return;
        }

        if (!sender.hasMetadata("ChatLab::privateMessage")) {
            sender.sendMessage(messagesFile.getString("no-reply"));
            return;
        }


        Player target = Bukkit.getPlayer(
                userManager.getUser(sender.getUniqueId()).getRepliedTarget());

        sender.sendMessage(configFile.getString("msg.from-sender")
                .replace("%target%", target.getName())
                .replace("%message%", senderMessage));

        target.sendMessage(configFile.getString("msg.from-receptor")
                .replace("%sender%", sender.getName())
                .replace("%message%", senderMessage));

        User targetUser = userManager.getUser(target.getUniqueId());

        if (targetUser.getRepliedTarget() != sender.getUniqueId()){

            targetUser.setRepliedTarget(sender.getUniqueId());
        }
    }

}

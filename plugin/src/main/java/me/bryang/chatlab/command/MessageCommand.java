package me.bryang.chatlab.command;

import me.bryang.chatlab.file.FileWrapper;
import me.bryang.chatlab.file.type.ConfigurationFile;
import me.bryang.chatlab.file.type.MessagesFile;
import me.bryang.chatlab.manager.SenderManager;
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
import team.unnamed.inject.InjectIgnore;

import java.util.Map;

@InjectAll
public class MessageCommand implements CommandClass {

    private FileWrapper<ConfigurationFile> configWrapper;
    private FileWrapper<MessagesFile> messagesWrapper;

    private Map<String, User> users;
    private SenderManager senderManager;
    @Command(names = {"msg", "pm", "m", "message", "tell", "w"},
            desc = "Private message command")
    public void messageCommand(@Sender Player sender, @OptArg() OfflinePlayer target,
                               @Text @OptArg("") String senderMessage) {

        ConfigurationFile configFile = configWrapper.get();
        MessagesFile messagesFile = messagesWrapper.get();


        if (target == null) {
            senderManager.sendMessage(sender, messagesFile.noArgumentMessage(),
                    Placeholder.unparsed("usage", "/msg <player> <message>"));
            return;
        }

        if (sender == target.getPlayer()){
            senderManager.sendMessage(sender, messagesFile.yourselfTalk());
            return;
        }

        if (!target.isOnline()) {
            senderManager.sendMessage(sender, messagesFile.playerOfflineMessage(),
                    Placeholder.unparsed("usage", "/msg <player> <message>"));
            return;
        }


        if (senderMessage.isEmpty()) {
            senderManager.sendMessage(sender, messagesFile.noArgumentMessage(),
                    Placeholder.unparsed("usage", "/msg <player> <message>"));
            return;
        }


        senderManager.sendMessage(sender, configFile.fromSenderMessage(),

                Placeholder.unparsed("target", target.getName()),
                Placeholder.unparsed("message", senderMessage));

        senderManager.sendMessage(target.getPlayer(), configFile.toReceptorMessage(),

                Placeholder.unparsed("sender", sender.getName()),
                Placeholder.unparsed("message", senderMessage));

        User senderUser = users.get(sender.getUniqueId().toString());
        User senderTarget = users.get(target.getUniqueId().toString());

        senderUser.recentMessenger(target.getUniqueId());
        senderTarget.recentMessenger(sender.getUniqueId());
    }
}

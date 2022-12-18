package me.bryang.chatlab.commands;

import jdk.jfr.Name;
import me.bryang.chatlab.managers.FileManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.OptArg;
import me.fixeddev.commandflow.annotated.annotation.Text;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Inject;
import javax.inject.Named;

@Command(
        names = {"msg", "pm", "m", "message", "tell", "w"},
        desc = "Private message command")


@InjectAll
public class MsgCommand implements CommandClass {

    @Named("config")
    private FileManager configFile;

    @Named("messages")
    private FileManager messagesFile;

    public void messageCommand(

            @Sender Player sender,
            @OptArg("") Player target,
            @Text @OptArg("") String senderMessage){

        if (target == null){
            sender.sendMessage(messagesFile.getString("no-argument")
                    .replace("%usage%", "/msg <player> <message>"));
            return;
        }

        if (senderMessage.isEmpty()){
            sender.sendMessage(messagesFile.getString("no-argument")
                    .replace("%usage%", "/msg <player> <message>"));
            return;
        }

        sender.sendMessage(configFile.getString("msg.from-sender")
                .replace("%target%", target.getName())
                .replace("%message%", senderMessage));
        target.sendMessage(configFile.getString("msg.from-receptor")
                .replace("%sender%", sender.getName())
                .replace("%message%", senderMessage));

    }
}

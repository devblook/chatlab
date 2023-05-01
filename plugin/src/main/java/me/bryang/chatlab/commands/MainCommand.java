package me.bryang.chatlab.commands;

import me.bryang.chatlab.FileCreator;
import me.bryang.chatlab.manager.SenderManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;

@Command(names = {"clab", "chatlab"})
@InjectAll
public class MainCommand implements CommandClass {

    private FileCreator configFile;
    @Named("messages")
    private FileCreator messagesFile;

    private SenderManager senderManager;

    @Command(names = "")
    public void mainSubCommand(@Sender Player sender) {
        senderManager.sendMessage(sender, "<blue>ChatLab: <white>Main plugin command.");
        senderManager.sendMessage(sender, "<grey>- <light_blue>/clab reload");
    }

    @Command(names = "reload", permission = "chatlab.reload")
    public void reloadSubCommand(@Sender Player sender) {

        configFile.reload();
        messagesFile.reload();
        senderManager.sendMessage(sender,"<blue>[ChatLab] <black>| <f>Plugin reloaded.");

    }

}

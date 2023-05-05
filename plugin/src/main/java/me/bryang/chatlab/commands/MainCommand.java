package me.bryang.chatlab.commands;

import me.bryang.chatlab.file.FileWrapper;
import me.bryang.chatlab.file.types.ConfigurationFile;
import me.bryang.chatlab.file.types.MessagesFile;
import me.bryang.chatlab.manager.SenderManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

@Command(names = {"clab", "chatlab"})
@InjectAll
public class MainCommand implements CommandClass {

    private FileWrapper<ConfigurationFile> configFile;
    private FileWrapper<MessagesFile> messagesFile;

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

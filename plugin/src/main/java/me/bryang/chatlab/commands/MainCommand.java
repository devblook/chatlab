package me.bryang.chatlab.commands;

import me.bryang.chatlab.FileCreator;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;

@Command(names = {"clab", "chatlab"})
@InjectAll
public class MainCommand implements CommandClass{

    private FileCreator configFile;
    @Named("messages")
    private FileCreator messagesFile;

    @Command(names = "")
    public void mainSubCommand(@Sender Player sender){
        sender.sendMessage("<red>ChatLab: <white>Main plugin command.");
        sender.sendMessage("<grey>- <light_blue>/clab reload");
    }

    @Command(names = "reload", permission = "chatlab.admin")
    public void reloadSubCommand(@Sender Player sender){

        configFile.reload();
        messagesFile.reload();
        sender.sendMessage("§a[ChatLab] §8| §fPlugin relaoded.");

    }

}

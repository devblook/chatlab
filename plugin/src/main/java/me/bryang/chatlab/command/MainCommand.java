package me.bryang.chatlab.command;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.configuration.section.MessageSection;
import me.bryang.chatlab.manager.MessageManager;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

@Command(names = {"clab", "chatlab"})
@InjectAll
public class MainCommand implements CommandClass {

    private ConfigurationContainer<RootSection> configurationContainer;
    private ConfigurationContainer<MessageSection> messageContainer;
    private MessageManager messageManager;

    @Command(names = "")
    public void mainSubCommand(@Sender Player sender) {
        messageManager.sendMessage(sender, "<blue>ChatLab: <white>Main plugin command.");
        messageManager.sendMessage(sender, "<dark_grey>- <white>/clab reload");
    }

    @Command(names = "reload")
    public void reloadSubCommand(@Sender Player sender) {

        if (!sender.hasPermission("clab.reload")) {
            messageManager.sendMessage(sender, this.messageContainer.get().error.noPermission);
            return;
        }

        configurationContainer.reload();
        messageContainer.reload();
        messageManager.sendMessage(sender, "<blue>[ChatLab] <black>| <white>Plugin has been reloaded.");

    }

}

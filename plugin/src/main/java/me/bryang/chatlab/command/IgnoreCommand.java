package me.bryang.chatlab.command;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.MessageSection;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.message.MessageManager;
import me.bryang.chatlab.user.User;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import team.unnamed.inject.InjectAll;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Command(names = "ignore")
@InjectAll
public class IgnoreCommand implements CommandClass {

    private MessageManager messageManager;
    private ConfigurationContainer<RootSection> configurationContainer;
    private ConfigurationContainer<MessageSection> messageContainer;
    private Map<String, User> userData;


    @Command(names = "")
    public void ignoreCommand(@Sender Player sender, OfflinePlayer target){

        RootSection rootSection = configurationContainer.get();
        MessageSection messageSection = messageContainer.get();

        if (sender.getUniqueId() == target.getUniqueId()){
            messageManager.sendMessage(sender, messageSection.error.yourselfIgnore);
            return;
        }

        if (target.getName().startsWith("-")){
            String command = target.getName().substring(1);
            
            if (command.equalsIgnoreCase("list")){
                Set<String> ignoredPlayers = userData.get(sender.getUniqueId().toString()).ignoredPlayers();

                int ignoredPlayersSize = ignoredPlayers.size();

                RootSection.Ignore.SeeIgnoredPlayers ignoredPlayersSector = rootSection.ignore.seeIgnoredPlayers;

                String ignoredPlayersData;
                if (ignoredPlayersSize != 0){

                    List<String> listIgnoredPlayers = new ArrayList<>();
                    ignoredPlayers
                            .forEach(fieldUniqueId -> listIgnoredPlayers
                                    .add(Bukkit.getOfflinePlayer(UUID.fromString(fieldUniqueId)).getName()));

                    ignoredPlayersData = String.join(", ", listIgnoredPlayers);
                }else{
                    ignoredPlayersData =  ignoredPlayersSector.error;
                }

                ignoredPlayersSector.format
                        .forEach(message -> messageManager.sendMessage(sender, message,
                                Placeholder.unparsed("ignored_players_size", String.valueOf(ignoredPlayers.size())),
                                Placeholder.unparsed("ignored_players_data", ignoredPlayersData)));
                return;
            }
        }
        
        if (!target.isOnline()){
            messageManager.sendMessage(sender, messageSection.error.playerOffline);
            return;
        }

        User user = userData.get(sender.getUniqueId().toString());

        if (user.containsIgnoredPlayers(target.getUniqueId())){
            messageManager.sendMessage(sender, messageSection.error.playerAlreadyIgnored,
                    Placeholder.unparsed("player", target.getName()));
            return;
        }

        user.ignore(target.getUniqueId());
        messageManager.sendMessage(sender, rootSection.ignore.ignoredPlayer,
                Placeholder.unparsed("player", target.getName()));

    }
}

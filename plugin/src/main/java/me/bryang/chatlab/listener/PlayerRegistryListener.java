package me.bryang.chatlab.listener;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.message.MessageManager;
import me.bryang.chatlab.user.User;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import team.unnamed.inject.InjectAll;

import java.util.Map;

@InjectAll
public class PlayerRegistryListener implements Listener {

    private Map<String, User> users;
    private ConfigurationContainer<RootSection> configurationContainer;
    private MessageManager messageManager;

    @EventHandler
    public void onRegistry(PlayerJoinEvent event) {

        if (!users.containsKey(event.getPlayer().getUniqueId().toString())){
            return;
        }

        users.put(event.getPlayer().getUniqueId().toString(), new User());
    }

    @EventHandler
    public void onUnRegistry(PlayerQuitEvent event) {

        User user = users.get(event.getPlayer().getUniqueId().toString());

        if (!user.hasRecentMessenger()) {
            return;
        }

        Player sender = Bukkit.getPlayer(user.recentMessenger());
        User target = users.get(user.recentMessenger().toString());

        messageManager.sendMessage(sender, configurationContainer.get().reply.left,
                Placeholder.unparsed("target", event.getPlayer().getName()));

        user.recentMessenger(null);
        target.recentMessenger(null);
    }
}

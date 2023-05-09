package me.bryang.chatlab.listener;

import me.bryang.chatlab.file.FileWrapper;
import me.bryang.chatlab.file.type.ConfigurationFile;
import me.bryang.chatlab.manager.SenderManager;
import me.bryang.chatlab.user.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import team.unnamed.inject.InjectAll;
import team.unnamed.inject.InjectIgnore;

import java.util.Map;

@InjectAll
public class PlayerRegistryListener implements Listener {

    private Map<String, User> users;

    private FileWrapper<ConfigurationFile> configWrapper;
    @InjectIgnore
    private final ConfigurationFile configFile = configWrapper.get();
    private SenderManager senderManager;


    @EventHandler
    public void onRegistry(PlayerJoinEvent event) {
        users.put(event.getPlayer().getUniqueId().toString(), new User());
    }

    @EventHandler
    public void onUnRegistry(PlayerQuitEvent event) {

        User user = users.get(event.getPlayer().getUniqueId().toString());


        if (!user.hasRecentMessenger()) {
            return;
        }

        Player sender = Bukkit.getPlayer(user.recentMessenger());

        senderManager.sendMessage(sender, configFile.leftMessage()
                .replace("%target%", event.getPlayer().getName()));

        user.recentMessenger(null);


    }
}

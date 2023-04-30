package me.bryang.chatlab.listeners;

import me.bryang.chatlab.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;
import java.util.Map;

public class PlayerRegistryListener implements Listener {

    @Inject
    private Map<String, User> users;

    @EventHandler
    public void onRegistry(PlayerJoinEvent event) {
        users.put(event.getPlayer().getUniqueId().toString(), new User());
    }

    @EventHandler
    public void onUnRegistry(PlayerQuitEvent event) {

    }
}

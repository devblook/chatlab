package me.bryang.chatlab.listener;

import me.bryang.chatlab.api.utils.TypeRegistry;
import me.bryang.chatlab.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;

public class PlayerRegistryListener implements Listener {

    @Inject
    private TypeRegistry<User> users;

    @EventHandler
    public void onRegistry(PlayerJoinEvent event) {
        users.register(new User(event.getPlayer().getUniqueId().toString()));
    }

    @EventHandler
    public void onUnRegistry(PlayerQuitEvent event) {
        users.unregister(event.getPlayer().getUniqueId().toString());
    }
}

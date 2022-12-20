package me.bryang.chatlab.listener;

import me.bryang.chatlab.user.UserManager;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;

public class PlayerJoinListener implements Listener{


    @Inject
    private UserManager userManager;

    public void joinEvent(PlayerJoinEvent event) {

        if (userManager.getUser(event.getPlayer().getUniqueId()) != null){
            userManager.createUser(event.getPlayer().getUniqueId());
        }
    }
}

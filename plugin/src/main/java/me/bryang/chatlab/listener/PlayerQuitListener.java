package me.bryang.chatlab.listener;

import me.bryang.chatlab.ChatLab;
import me.bryang.chatlab.user.User;
import me.bryang.chatlab.user.UserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;
import java.util.UUID;

public class PlayerQuitListener implements Listener {

    @Inject
    private UserManager userManager;

    public void leaveEvent(PlayerQuitEvent event){

        if (userManager.getUser(event.getPlayer().getUniqueId()).getRepliedTarget() != null){

            User senderUser = userManager.getUser(event.getPlayer().getUniqueId());

            User targetUser = userManager.getUser(
                    userManager.getUser(event.getPlayer().getUniqueId()).getRepliedTarget());

            senderUser.setRepliedTarget(null);

            if (targetUser.getRepliedTarget() == event.getPlayer().getUniqueId()){
                targetUser.setRepliedTarget(null);
            }

        }
    }
}

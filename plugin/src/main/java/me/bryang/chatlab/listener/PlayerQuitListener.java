package me.bryang.chatlab.listener;

import me.bryang.chatlab.ChatLab;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;
import java.util.UUID;

public class PlayerQuitListener implements Listener {

    @Inject
    private ChatLab chatLab;

    public void leaveEvent(PlayerQuitEvent event){

        if (event.getPlayer().hasMetadata("ChatLab::sendMessage")){

            Player sender = event.getPlayer();
            Player target =  Bukkit.getPlayer(
                    UUID.fromString(sender.getMetadata("ChatLab::privateMessage").get(0).asString()));

            sender.removeMetadata("ChatLab::sendMessage", chatLab);

            if (target.getMetadata("ChatLab::sendMessage").get(0).asString()
                    .equalsIgnoreCase(sender.getUniqueId().toString())){

                target.removeMetadata("ChatLab::sendMessage", chatLab);
            }

        }
    }
}

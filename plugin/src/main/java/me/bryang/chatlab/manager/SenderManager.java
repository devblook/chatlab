package me.bryang.chatlab.manager;

import me.bryang.chatlab.ChatLab;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public class SenderManager {

    private final BukkitAudiences audience;
    private final MiniMessage miniMessage;

    public SenderManager(ChatLab chatLab){
        audience = BukkitAudiences.create(chatLab);
        miniMessage = MiniMessage.miniMessage();
    }

    public void sendMessage(Player sender, String message){
        audience.player(sender).sendMessage(miniMessage.deserialize(message));
    }
}

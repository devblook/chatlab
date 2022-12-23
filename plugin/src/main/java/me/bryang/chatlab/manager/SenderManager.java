package me.bryang.chatlab.manager;

import me.bryang.chatlab.ChatLab;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;

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

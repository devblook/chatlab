package me.bryang.chatlab.manager;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;

public class SenderManager {

    private final MiniMessage miniMessage;

    public SenderManager() {
        miniMessage = MiniMessage
                .builder()
                .build();
    }

    public void sendMessage(Player sender, String message) {
        sender.sendMessage(miniMessage.deserialize(message));
    }

    public void sendMessage(Player sender, String message, TagResolver... tagResolver) {
        sender.sendMessage(miniMessage.deserialize(message, tagResolver));
    }
}

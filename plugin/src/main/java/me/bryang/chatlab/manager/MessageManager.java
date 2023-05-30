package me.bryang.chatlab.manager;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.entity.Player;

import javax.inject.Singleton;

@Singleton
public class MessageManager {

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    public void sendMessage(Player sender, String message) {
        sender.sendMessage(MINI_MESSAGE.deserialize(message));
    }

    public void sendMessage(Player sender, String message, TagResolver... tagResolver) {
        sender.sendMessage(format(message, tagResolver));
    }

    public Component format(String message, TagResolver... tagResolver){
        return MINI_MESSAGE.deserialize(message, tagResolver);

    }
}

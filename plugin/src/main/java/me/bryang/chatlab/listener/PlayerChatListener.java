package me.bryang.chatlab.listener;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatDecorateEvent;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.manager.MessageManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.text.serializer.plain.PlainComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.slf4j.Logger;
import team.unnamed.inject.InjectAll;


@InjectAll
public class PlayerChatListener implements Listener {

    private ConfigurationContainer<RootSection> configurationContainer;
    private MessageManager messageManager;


    @EventHandler
    public void onChat(AsyncChatEvent event) {

        if (!configurationContainer.get().chatFormat.enabled) {
            return;
        }

        event.renderer((source, sourceDisplayName, message, viewer) -> {

            String formattedMessage = PlainTextComponentSerializer.plainText().serialize(message);

            TagResolver.Single messagePlaceholder;

            if (source.hasPermission("chatlab.tags")){
                messagePlaceholder = Placeholder.unparsed("<message>",formattedMessage);
            }else{
                messagePlaceholder = Placeholder.unparsed("<message>", formattedMessage);
            }


            return messageManager.format(
                            configurationContainer.get().chatFormat.format,

                            Placeholder.unparsed("player", source.getName()),
                            messagePlaceholder);

        });

    }
}

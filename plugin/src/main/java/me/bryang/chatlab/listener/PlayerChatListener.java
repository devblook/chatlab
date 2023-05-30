package me.bryang.chatlab.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.manager.MessageManager;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.event.Listener;
import team.unnamed.inject.InjectAll;

import javax.inject.Inject;


@InjectAll
public class PlayerChatListener implements Listener {

    private ConfigurationContainer<RootSection> configurationContainer;
    private MessageManager messageManager;

    public void onChat(AsyncChatEvent event) {

        if (!configurationContainer.get().chatFormat.enabled) {
            return;
        }

        event.message(
                messageManager.format(
                        configurationContainer.get().chatFormat.format,

                        Placeholder.unparsed("%player%", event.getPlayer().getName()),
                        Placeholder.unparsed("%message%", event.originalMessage().toString())));

    }
}

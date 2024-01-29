package me.bryang.chatlab.listener;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.bryang.chatlab.chat.GroupFormatHandler;
import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.message.MessageManager;
import me.bryang.chatlab.storage.repository.Repository;
import me.bryang.chatlab.storage.user.User;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.slf4j.Logger;
import team.unnamed.inject.InjectAll;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@InjectAll
public class PlayerChatListener implements Listener {

	@Named("users")
	private Repository<User> userRepository;

	private ConfigurationContainer<RootSection> configurationContainer;

	private MessageManager messageManager;
	private GroupFormatHandler groupFormatHandler;

	private Logger logger;

	@EventHandler(priority = EventPriority.LOW)
	public void onChat(AsyncChatEvent event) {

		Player sender = event.getPlayer();
		event.viewers().removeIf(audience -> {

			if (audience instanceof Player player) {
				List<String> ignoredPlayers = userRepository.findById(player.getUniqueId().toString()).ignoredPlayers();

				if (sender.hasPermission("clab.ignore-bypass")) {
					return false;
				}

				return ignoredPlayers.contains(sender.getUniqueId().toString());
			}
			return false;
		});

		if (!configurationContainer.get().chatFormat.enabled) {
			return;
		}

		event.renderer((source, sourceDisplayName, message, viewer) -> {

			String formattedMessage = PlainTextComponentSerializer
				.plainText()
				.serialize(message);

			TagResolver.Single messagePlaceholder;

			messagePlaceholder = source.hasPermission("clab.tags")
				? Placeholder.parsed("message", formattedMessage)
				: Placeholder.unparsed("message", formattedMessage);

			;
			String format = groupFormatHandler.format(sender);

			return messageManager.formatChat(source, format, messagePlaceholder);

		});
	}
}

package me.bryang.chatlab.message;

import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.inject.Singleton;

@Singleton
public class MessageManager {

	private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

	public void sendMessage(Player sender, String message) {
		sender.sendMessage(MINI_MESSAGE.deserialize(message));
	}

	public void sendMessage(Player sender, String message, TagResolver... tagResolver) {
		sender.sendMessage(MINI_MESSAGE.deserialize(message, tagResolver));
	}

	public Component formatChat(Player target, String formatMessage, TagResolver messageResolver) {

		TagResolver.Builder tagBuilder = TagResolver.builder();
		tagBuilder
			.resolvers(
				messageResolver,
				Placeholder.unparsed("player", target.getName()));

		if (Bukkit.getPluginManager().isPluginEnabled("MiniPlaceholders")) {
			tagBuilder
				.resolvers(
					MiniPlaceholders.getGlobalPlaceholders(),
					MiniPlaceholders.getAudiencePlaceholders(target));
		}

		return MINI_MESSAGE.deserialize(formatMessage, tagBuilder.build());

	}
}

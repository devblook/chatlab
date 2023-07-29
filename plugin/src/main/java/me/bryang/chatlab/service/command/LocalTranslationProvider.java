package me.bryang.chatlab.service.command;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.MessageSection;
import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.translator.TranslationProvider;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LocalTranslationProvider implements TranslationProvider {

	@Inject
	private ConfigurationContainer<MessageSection> messageContainer;

	@Override
	public String getTranslation(Namespace namespace, String path) {
		MessageSection messageSection = messageContainer.get();

		String text = switch (path) {
			case "sender.only-player" -> messageSection.error.console;
			case "player.offline" -> messageSection.error.playerOffline
				.replace("<player>", "%s");
			case "command.no-permission" -> messageSection.error.noPermission;

			default -> "If u see this message, please contact in the discord support. Key: " + path;
		};


		return LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize(text));


	}
}

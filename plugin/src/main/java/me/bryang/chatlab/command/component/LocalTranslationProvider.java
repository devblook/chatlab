package me.bryang.chatlab.command.component;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.MessageSection;
import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.translator.TranslationProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LocalTranslationProvider implements TranslationProvider {

	@Inject
	private ConfigurationContainer<MessageSection> messageContainer;

	@Override
	public String getTranslation(Namespace namespace, String path) {
		MessageSection messageSection = messageContainer.get();

		return switch (path) {
			case "sender.only-player" -> messageSection.error.console;
			case "player.offline" -> messageSection.error.playerOffline
				.replace("<player>", "<argument>");
			case "command.no-permission" -> messageSection.error.noPermission;

			default -> "If u see this message, please contact in the discord support. Key: " + path;
		};
	}
}

package me.bryang.chatlab.service.command.builder;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.MessageSection;
import me.fixeddev.commandflow.CommandContext;
import me.fixeddev.commandflow.usage.UsageBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LocalUsageBuilder implements UsageBuilder {

	@Inject
	private ConfigurationContainer<MessageSection> messageContainer;

	@Override
	public Component getUsage(CommandContext commandContext) {

		if (commandContext.getArguments().isEmpty()) {
			return MiniMessage.miniMessage().deserialize(messageContainer.get().error.noArgument,
				Placeholder.unparsed("usage", "/clab help"));
		}

		MessageSection messageSection = messageContainer.get();

		String commandName = commandContext.getCommand().getName().toUpperCase();
		CommandUsage commandUsage = CommandUsage.valueOf(commandName);

		return MiniMessage.miniMessage().deserialize(messageSection.error.noArgument,
			Placeholder.unparsed("usage", commandUsage.usage()));
	}
}

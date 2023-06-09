package me.bryang.chatlab.service.command.builder;

import me.fixeddev.commandflow.CommandContext;
import me.fixeddev.commandflow.usage.UsageBuilder;
import net.kyori.text.Component;
import net.kyori.text.TranslatableComponent;

import javax.inject.Singleton;

@Singleton
public class CommandUsageBuilder implements UsageBuilder {

	@Override
	public Component getUsage(CommandContext commandContext) {

		String command = commandContext.getCommand().getName();
		ArgumentList argumentList = ArgumentList.valueOf(command.toUpperCase());

		return TranslatableComponent.of("command.subcommand.invalid|" + argumentList.getUsage());

	}
}

package me.bryang.chatlab.service.command.argument;

import me.fixeddev.commandflow.CommandContext;
import me.fixeddev.commandflow.usage.UsageBuilder;
import net.kyori.text.Component;
import net.kyori.text.TranslatableComponent;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class CommandUsageBuilder implements UsageBuilder {

    @Inject
    private Logger logger;

    @Override
    public Component getUsage(CommandContext commandContext) {

        String command = commandContext.getCommand().getName();
        ArgumentList argumentList = ArgumentList.valueOf(command.toUpperCase());

        return TranslatableComponent.of("command.subcommand.invalid | " + argumentList.getUsage());

    }
}

package me.bryang.chatlab.module.submodule;

import me.bryang.chatlab.command.*;
import me.fixeddev.commandflow.annotated.CommandClass;
import team.unnamed.inject.AbstractModule;

public class CommandModule extends AbstractModule {

	@Override
	public void configure() {
		multibind(CommandClass.class)
			.asSet()
			.to(MainCommand.class)
			.to(MessageCommand.class)
			.to(ToggleMsgCommand.class)
			.to(ReplyCommand.class)
			.to(IgnoreCommand.class)
			.to(UnIgnoreCommand.class);
	}
}

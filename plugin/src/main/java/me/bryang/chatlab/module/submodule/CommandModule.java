package me.bryang.chatlab.module.submodule;

import me.bryang.chatlab.command.MainCommand;
import me.bryang.chatlab.command.MessageCommand;
import me.bryang.chatlab.command.ReplyCommand;
import me.fixeddev.commandflow.annotated.CommandClass;
import team.unnamed.inject.AbstractModule;

public class CommandModule extends AbstractModule {

    @Override
    public void configure() {
        multibind(CommandClass.class).asSet()
                .to(MainCommand.class)
                .to(MessageCommand.class)
                .to(ReplyCommand.class);
    }
}

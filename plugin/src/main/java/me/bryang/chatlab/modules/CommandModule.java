package me.bryang.chatlab.modules;

import me.bryang.chatlab.commands.MainCommand;
import me.bryang.chatlab.commands.MessageCommand;
import me.bryang.chatlab.commands.ReplyCommand;
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

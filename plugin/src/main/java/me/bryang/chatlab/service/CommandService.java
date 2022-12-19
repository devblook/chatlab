package me.bryang.chatlab.service;

import me.bryang.chatlab.api.Service;
import me.bryang.chatlab.command.MsgCommand;
import me.bryang.chatlab.command.ReplyCommand;
import me.bryang.chatlab.service.translator.CommandTranslatorLoader;
import me.bryang.chatlab.manager.FileManager;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;

import javax.inject.Inject;
import javax.inject.Named;

public class CommandService implements Service {

    @Inject @Named("messages")
    private FileManager messagesFile;

    private AnnotatedCommandTreeBuilder builder;
    private CommandManager commandManager;

    @Override
    public void start(){

        commandManager = new BukkitCommandManager("ChatLab");
        commandManager.getTranslator().setProvider(new CommandTranslatorLoader(messagesFile));
        PartInjector partInjector = PartInjector.create();

        partInjector.install(new DefaultsModule());
        partInjector.install(new BukkitModule());

        builder = new AnnotatedCommandTreeBuilderImpl(partInjector);
        registerCommands(new MsgCommand(), new ReplyCommand());
    }

    public void registerCommands(CommandClass... commandClasses){

        for (CommandClass commandClass : commandClasses){

            commandManager.registerCommands(builder.fromClass(commandClass));

        }

    }

    @Override
    public void stop() {
        commandManager.unregisterAll();
    }
}

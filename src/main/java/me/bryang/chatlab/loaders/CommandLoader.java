package me.bryang.chatlab.loaders;

import me.bryang.chatlab.Loader;
import me.bryang.chatlab.commands.MsgCommand;
import me.bryang.chatlab.loaders.translator.CommandTranslator;
import me.bryang.chatlab.managers.FileManager;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;

import javax.inject.Inject;
import javax.inject.Named;

public class CommandLoader implements Loader{

    @Inject @Named("messages")
    private FileManager messagesFile;

    private AnnotatedCommandTreeBuilder builder;
    private CommandManager commandManager;

    @Override
    public void load(){

        commandManager = new BukkitCommandManager("ChatLab");
        commandManager.getTranslator().setProvider(new CommandTranslator(messagesFile));
        PartInjector partInjector = PartInjector.create();

        partInjector.install(new DefaultsModule());
        partInjector.install(new BukkitModule());

        builder = new AnnotatedCommandTreeBuilderImpl(partInjector);
        registerCommands(new MsgCommand());
    }

    public void registerCommands(CommandClass... commandClasses){

        for (CommandClass commandClass : commandClasses){

            commandManager.registerCommands(builder.fromClass(commandClass));

        }

    }


}

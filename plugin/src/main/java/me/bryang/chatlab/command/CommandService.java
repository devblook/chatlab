package me.bryang.chatlab.command;

import me.bryang.chatlab.api.Service;
<<<<<<< HEAD:plugin/src/main/java/me/bryang/chatlab/service/CommandService.java
import me.bryang.chatlab.command.MainCommand;
import me.bryang.chatlab.command.MsgCommand;
import me.bryang.chatlab.command.ReplyCommand;
import me.bryang.chatlab.service.translator.CommandTranslatorLoader;
import me.bryang.chatlab.manager.FileManager;
=======
import me.bryang.chatlab.command.translator.CommandCustomTranslator;
import me.bryang.chatlab.manager.BukkitFileManager;
>>>>>>> cfc6452564f5dfd3aa5aeccf60a2d74fd869cc22:plugin/src/main/java/me/bryang/chatlab/command/CommandService.java
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
import java.util.Set;

public class CommandService implements Service {

    @Inject
    @Named("messages")
    private BukkitFileManager messagesFile;
    @Inject
    private Set<CommandClass> commands;
    private CommandManager commandManager;

    @Override
    public void start() {
        commandManager = new BukkitCommandManager("ChatLab");
        commandManager.getTranslator().setProvider(new CommandCustomTranslator(messagesFile.get()));
        PartInjector partInjector = PartInjector.create();

        partInjector.install(new DefaultsModule());
        partInjector.install(new BukkitModule());

<<<<<<< HEAD:plugin/src/main/java/me/bryang/chatlab/service/CommandService.java
        builder = new AnnotatedCommandTreeBuilderImpl(partInjector);
        registerCommands(new MsgCommand(), new ReplyCommand(), new MainCommand());
    }

    public void registerCommands(CommandClass... commandClasses){

        for (CommandClass commandClass : commandClasses){

            commandManager.registerCommands(builder.fromClass(commandClass));

=======
        AnnotatedCommandTreeBuilder builder = new AnnotatedCommandTreeBuilderImpl(partInjector);
        for (CommandClass command : commands) {
            commandManager.registerCommands(builder.fromClass(command));
>>>>>>> cfc6452564f5dfd3aa5aeccf60a2d74fd869cc22:plugin/src/main/java/me/bryang/chatlab/command/CommandService.java
        }
    }

    @Override
    public void stop() {
        commandManager.unregisterAll();
    }
}

package me.bryang.chatlab.services.translator;

import me.bryang.chatlab.services.Service;
import me.bryang.chatlab.FileCreator;
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


public class CommandServices implements Service {

    @Inject
    @Named("messages")
    private FileCreator messagesFile;

    @Inject
    private Set<CommandClass> commands;

    @Override
    public void start() {

        CommandManager commandManager = new BukkitCommandManager("ChatLab");

        commandManager.getTranslator().setProvider(new CommandCustomTranslator(messagesFile.get()));
        PartInjector partInjector = PartInjector.create();

        partInjector.install(new DefaultsModule());
        partInjector.install(new BukkitModule());

        AnnotatedCommandTreeBuilder builder = new AnnotatedCommandTreeBuilderImpl(partInjector);
        for (CommandClass command : commands) {
            commandManager.registerCommands(builder.fromClass(command));
        }
    }

}
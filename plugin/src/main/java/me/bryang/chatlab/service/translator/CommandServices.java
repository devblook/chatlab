package me.bryang.chatlab.service.translator;

import me.bryang.chatlab.service.Service;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;
import team.unnamed.inject.InjectAll;

import java.util.Set;

@InjectAll
public class CommandServices implements Service {

    private Set<CommandClass> commands;
    private CommandCustomTranslator commandCustomTranslator;

    @Override
    public void start() {
        CommandManager commandManager = new BukkitCommandManager("ChatLab");

        commandManager.getTranslator().setProvider(commandCustomTranslator);
        PartInjector partInjector = PartInjector.create();

        partInjector.install(new DefaultsModule());
        partInjector.install(new BukkitModule());

        AnnotatedCommandTreeBuilder builder = new AnnotatedCommandTreeBuilderImpl(partInjector);
        for (CommandClass command : commands) {
            commandManager.registerCommands(builder.fromClass(command));
        }
    }

}
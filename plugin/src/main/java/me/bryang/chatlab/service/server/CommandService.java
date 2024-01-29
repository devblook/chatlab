package me.bryang.chatlab.service.server;

import me.bryang.chatlab.command.component.LocalTranslationProvider;
import me.bryang.chatlab.command.component.builder.LocalUsageBuilder;
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
public class CommandService implements Service {

	private Set<CommandClass> commands;
	private LocalTranslationProvider commandTranslatorHandler;
	private LocalUsageBuilder localUsageBuilder;

	@Override
	public void start() {

		CommandManager commandManager = new BukkitCommandManager("ChatLab");

		commandManager.setUsageBuilder(localUsageBuilder);
		commandManager.getTranslator().setProvider(commandTranslatorHandler);

		PartInjector partInjector = PartInjector.create();

		partInjector.install(new DefaultsModule());
		partInjector.install(new BukkitModule());

		AnnotatedCommandTreeBuilder builder = new AnnotatedCommandTreeBuilderImpl(partInjector);
		commands
			.forEach(commandClass -> commandManager.registerCommands(builder.fromClass(commandClass)));
	}
}
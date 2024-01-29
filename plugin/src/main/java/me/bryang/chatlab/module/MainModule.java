package me.bryang.chatlab.module;

import me.bryang.chatlab.ChatLab;
import me.bryang.chatlab.module.submodule.plugin.ConditionModule;
import me.bryang.chatlab.module.submodule.plugin.ConfigurationModule;
import me.bryang.chatlab.module.submodule.plugin.ServiceModule;
import me.bryang.chatlab.module.submodule.plugin.StorageModule;
import me.bryang.chatlab.module.submodule.server.CommandModule;
import me.bryang.chatlab.module.submodule.server.ListenerModule;
import org.slf4j.Logger;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.Provides;

import javax.inject.Named;
import javax.inject.Singleton;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainModule extends AbstractModule {

	private final ChatLab plugin;

	public MainModule(ChatLab plugin) {
		this.plugin = plugin;

	}

	@Provides @Singleton
	private Logger provideLogger(ChatLab plugin) {
		return plugin.getSLF4JLogger();
	}
	@Provides @Singleton @Named("async-thread")
	public ExecutorService asyncThreadExecutor(){
		return Executors.newSingleThreadExecutor();
	}
	@Override
	public void configure() {
		bind(ChatLab.class)
			.toInstance(plugin);

		bind(Path.class)
			.named("plugin-folder")
			.toInstance(plugin.getDataFolder().toPath());
		bind(String.class)
			.named("plugin-version")
			.toInstance(plugin.getPluginMeta().getVersion());

		install(
			new ConfigurationModule(),
			new ConditionModule(),
			new StorageModule(),
			new ListenerModule(),
			new CommandModule(),
			new ServiceModule());

	}
}

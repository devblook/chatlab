package me.bryang.chatlab.module.submodule;

import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.MessageSection;
import me.bryang.chatlab.configuration.section.RootSection;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.Provides;

import javax.inject.Named;
import javax.inject.Singleton;
import java.nio.file.Path;

public class ConfigurationModule extends AbstractModule {

	@Provides
	@Singleton
	private ConfigurationContainer<RootSection> provideRootConfiguration(@Named("plugin-folder") Path pluginPath) {
		return new ConfigurationContainer<>("config", pluginPath, RootSection.class);
	}

	@Provides
	@Singleton
	private ConfigurationContainer<MessageSection> provideMessageConfiguration(@Named("plugin-folder") Path pluginPath) {
		return new ConfigurationContainer<>("messages", pluginPath, MessageSection.class);
	}

}

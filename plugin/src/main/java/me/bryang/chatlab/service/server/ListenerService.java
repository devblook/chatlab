package me.bryang.chatlab.service.server;

import me.bryang.chatlab.ChatLab;
import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.service.Service;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import team.unnamed.inject.InjectAll;

import java.util.Set;

@InjectAll
public class ListenerService implements Service {

	private ChatLab plugin;

	private ConfigurationContainer<RootSection> configContainer;

	private Set<Listener> listeners;

	@Override
	public void start() {

		PluginManager pluginManager = plugin.getServer().getPluginManager();

		listeners
			.forEach(listener -> pluginManager.registerEvents(listener, plugin));
	}
}

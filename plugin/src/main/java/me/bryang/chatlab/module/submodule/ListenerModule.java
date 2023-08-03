package me.bryang.chatlab.module.submodule;

import me.bryang.chatlab.listener.PlayerChatListener;
import me.bryang.chatlab.listener.PlayerRegistryListener;
import org.bukkit.event.Listener;
import team.unnamed.inject.AbstractModule;

public class ListenerModule extends AbstractModule {

	@Override
	public void configure() {
		multibind(Listener.class)
			.asSet()
			.to(PlayerRegistryListener.class)
			.to(PlayerChatListener.class);
	}

}

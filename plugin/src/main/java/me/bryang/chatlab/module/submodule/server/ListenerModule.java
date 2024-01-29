package me.bryang.chatlab.module.submodule.server;

import me.bryang.chatlab.listener.PlayerChatListener;
import me.bryang.chatlab.listener.PlayerConnectListener;
import org.bukkit.event.Listener;
import team.unnamed.inject.AbstractModule;

public class ListenerModule extends AbstractModule {

	@Override
	public void configure() {
		multibind(Listener.class)
			.asSet()
			.to(PlayerConnectListener.class)
			.to(PlayerChatListener.class);
	}
}

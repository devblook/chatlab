package me.bryang.chatlab.service.plugin;

import me.bryang.chatlab.chat.GroupFormatHandler;
import me.bryang.chatlab.service.Service;
import team.unnamed.inject.InjectAll;

@InjectAll
public class ManagerService implements Service {


	private GroupFormatHandler groupFormatHandler;

	@Override
	public void start() {

		groupFormatHandler.load();
	}

	@Override
	public void stop() {

	}
}

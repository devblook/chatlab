package me.bryang.chatlab.module.submodule.plugin;

import me.bryang.chatlab.service.Service;
import me.bryang.chatlab.service.plugin.HookService;
import me.bryang.chatlab.service.plugin.ManagerService;
import me.bryang.chatlab.service.plugin.StorageService;
import me.bryang.chatlab.service.server.CommandService;
import me.bryang.chatlab.service.server.ListenerService;
import team.unnamed.inject.AbstractModule;

public class ServiceModule extends AbstractModule {

	@Override
	public void configure() {
		multibind(Service.class).asSet()
			.to(CommandService.class)
			.to(ListenerService.class)
			.to(StorageService.class)
			.to(ManagerService.class)
			.to(HookService.class);
	}
}

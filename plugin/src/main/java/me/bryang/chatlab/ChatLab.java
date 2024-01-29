package me.bryang.chatlab;

import me.bryang.chatlab.module.MainModule;
import me.bryang.chatlab.service.Service;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import team.unnamed.inject.Injector;

import javax.inject.Inject;
import java.util.Set;

public class ChatLab extends JavaPlugin{

	@Inject
	private Set<Service> services;
	@Inject
	private Logger logger;
	@Inject
	private UpdateCheckHandler updateChecker;

	@Override
	public void onLoad() {

		Injector.create(new MainModule(this))
			.injectMembers(this);

		updateChecker.init();
		updateChecker.request();
	}

	@Override
	public void onEnable() {

		services.forEach(Service::start);

		logger.info("Loaded services.");
		logger.info("Thanks for using my plugin!");
	}

	@Override
	public void onDisable() {
		services.forEach(Service::stop);
	}

}

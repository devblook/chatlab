package me.bryang.chatlab.service.plugin;

import me.bryang.chatlab.service.Service;
import me.bryang.chatlab.storage.user.gson.GsonStorageManager;

import javax.inject.Inject;

public class StorageService implements Service {

	@Inject
	private GsonStorageManager gsonStorageManager;


	@Override
	public void start() {
		gsonStorageManager.init();
	}

	@Override
	public void stop() {
		gsonStorageManager.stop();
	}
}

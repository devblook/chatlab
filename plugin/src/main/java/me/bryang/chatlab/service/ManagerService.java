package me.bryang.chatlab.service;

import me.bryang.chatlab.user.UserDataHandler;

import javax.inject.Inject;

public class ManagerService implements Service {

	@Inject
	private UserDataHandler userDataHandler;

	@Override
	public void start() {

		userDataHandler.start();
	}

	@Override
	public void stop() {
		userDataHandler.stop();
	}
}

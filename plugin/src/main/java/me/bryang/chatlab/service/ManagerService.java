package me.bryang.chatlab.service;

import me.bryang.chatlab.manager.UserDataManager;
import me.bryang.chatlab.message.authorizer.MessageAuthorizer;

import javax.inject.Inject;

public class ManagerService implements Service {

	@Inject
	private UserDataManager userDataManager;
	@Inject
	private MessageAuthorizer messageAuthorizer;

	@Override
	public void start() {
		userDataManager.init();
		messageAuthorizer.init();
	}

	@Override
	public void stop() {
		userDataManager.stop();
	}
}

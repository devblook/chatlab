package me.bryang.chatlab.service;

public interface Service {

	void start();

	default void stop() {
		// do nothing
	}
}

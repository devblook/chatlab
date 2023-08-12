package me.bryang.chatlab.module.submodule;

import me.bryang.chatlab.user.User;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.key.TypeReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(new TypeReference<Map<String, User>>(){})
			.named("users")
			.toInstance(new HashMap<>());

		bind(new TypeReference<List<User>>() {})
			.named("users-in-spy")
			.toInstance(new ArrayList<>());
	}
}

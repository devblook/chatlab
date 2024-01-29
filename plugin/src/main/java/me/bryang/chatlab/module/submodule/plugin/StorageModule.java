package me.bryang.chatlab.module.submodule.plugin;

import me.bryang.chatlab.storage.repository.Repository;
import me.bryang.chatlab.storage.repository.UserRepositoryImpl;
import me.bryang.chatlab.storage.user.User;
import team.unnamed.inject.AbstractModule;
import team.unnamed.inject.key.TypeReference;

import java.util.ArrayList;
import java.util.List;

public class StorageModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(new TypeReference<Repository<User>>(){})
			.named("users")
			.toInstance(new UserRepositoryImpl());

		bind(new TypeReference<List<String>>() {})
			.named("users-in-spy")
			.toInstance(new ArrayList<>());
	}
}

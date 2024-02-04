package me.bryang.chatlab.storage.repository;

import me.bryang.chatlab.storage.user.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepositoryImpl implements Repository<User>{

	private final Map<String, User> users = new ConcurrentHashMap<>();

	@Override
	public void create(User user) {
		users.put(user.id(), user);
	}

	@Override
	public User findById(String id) {
		return users.get(id);
	}

	@Override
	public boolean exists(String id) {
		return users.containsKey(id);
	}

	@Override
	public void deleteById(String id) {
		users.remove(id);
	}

	@Override
	public void update(User user) {
		users.replace(user.id(), user);
	}

	@Override
	public Collection<User> findAll(){
		return users.values();
	}

}

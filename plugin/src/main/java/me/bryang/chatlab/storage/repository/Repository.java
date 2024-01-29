package me.bryang.chatlab.storage.repository;

import java.util.Collection;

public interface Repository<T> {

	void create(T t);

	T findById(String id);

	boolean exists(String id);

	void deleteById(String id);

	void update(T t);

	Collection<T> findAll();

}

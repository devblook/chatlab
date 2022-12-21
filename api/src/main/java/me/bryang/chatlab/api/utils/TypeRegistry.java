package me.bryang.chatlab.api.utils;

import me.bryang.chatlab.api.Identifiable;

import java.util.Collection;

public interface TypeRegistry<T extends Identifiable> extends Iterable<T> {

    T register(T type);

    T unregister(T type);

    T unregister(String id);

    T get(String id);

    boolean contains(String id);

    default void addAll(Collection<T> types) {
        types.forEach(this::register);
    }

    int size();
}

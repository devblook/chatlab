package me.bryang.chatlab.api.utils;

import me.bryang.chatlab.api.Identifiable;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TypeRegistryImpl<T extends Identifiable> implements TypeRegistry<T> {

    private final Map<String, T> typeMap = new ConcurrentHashMap<>();

    @Override
    public T register(T type) {
        return typeMap.put(type.id(), type);
    }

    @Override
    public T unregister(T type) {
        return typeMap.remove(type.id());
    }

    @Override
    public T unregister(String id) {
        return typeMap.remove(id);
    }

    @Override
    public T get(String id) {
        return typeMap.get(id);
    }

    @Override
    public boolean contains(String id) {
        return typeMap.containsKey(id);
    }

    @Override
    public int size() {
        return typeMap.size();
    }

    @Override
    public Iterator<T> iterator() {
        return typeMap.values().iterator();
    }
}

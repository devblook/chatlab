package me.bryang.chatlab.user;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {

    private final Map<UUID, User> userMap = new HashMap<>();

    public void createUser(UUID uuid){
        userMap.put(uuid, new User());
    }

    public void deleteUser(UUID uuid){
        userMap.put(uuid, new User());
    }

    public User getUser(UUID uuid){
        return userMap.get(uuid);
    }
}

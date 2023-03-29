package me.bryang.chatlab.user;

import me.bryang.chatlab.api.Identifiable;

import javax.annotation.Nullable;
import java.util.UUID;

public class User implements Identifiable {

    private final String id;
    private UUID recentMessenger;

    public User(String id) {
        this.id = id;
    }

    // Start customizable data user

    @Nullable
    public UUID recentMessenger() {
        return recentMessenger;
    }

    public void recentMessenger(UUID messenger) {
        this.recentMessenger = messenger;
    }

    public boolean hasRecentMessenger() {
        return recentMessenger != null;
    }

    // End customizable data user

    @Override
    public String id() {
        return id;
    }

}

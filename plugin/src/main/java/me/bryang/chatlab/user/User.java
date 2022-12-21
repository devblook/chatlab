package me.bryang.chatlab.user;

import me.bryang.chatlab.api.Identifiable;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.UUID;

public class User implements Identifiable {

    private String id;
    private String recentMessenger;

    public User(String id) {
        this.id = id;
    }

    // Start customizable data user

    @Nullable
    public String recentMessenger() {
        return recentMessenger;
    }

    public void recentMessenger(String messenger) {
        this.recentMessenger = recentMessenger;
    }

    public boolean hasRecentMessenger() {
        return recentMessenger != null;
    }

    // End customizable data user

    @Override
    public String id() {
        return id;
    }

    public Player bukkitPlayer() {
        return Bukkit.getPlayer(UUID.fromString(id));
    }
}

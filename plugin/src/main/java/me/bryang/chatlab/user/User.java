package me.bryang.chatlab.user;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class User {

    private UUID recentMessenger;
    private Set<String> ignoredPlayers;


    @Nullable
    public UUID recentMessenger() {
        return this.recentMessenger;
    }

    public void recentMessenger(UUID messenger) {
        this.recentMessenger = messenger;
    }

    public boolean hasRecentMessenger() {
        return this.recentMessenger != null;
    }

    public void putIgnorePlayers(Set<String> ignoredPlayers) {
        this.ignoredPlayers = ignoredPlayers;
    }

    public void ignore(UUID target) {
        ignoredPlayers.add(target.toString());
    }

    public void unIgnore(UUID target){
        ignoredPlayers.remove(target.toString());
    }

    public boolean containsIgnoredPlayers(UUID target){
        return ignoredPlayers.contains(target.toString());
    }
}

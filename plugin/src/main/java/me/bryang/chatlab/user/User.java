package me.bryang.chatlab.user;

import javax.annotation.Nullable;
import java.util.UUID;

public class User  {

    private UUID recentMessenger;

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



}

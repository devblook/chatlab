package me.bryang.chatlab.user;

import javax.annotation.Nullable;
import java.util.UUID;

public class User {

    private UUID recentMessenger;


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


}

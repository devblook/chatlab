package me.bryang.chatlab.authorizer.tasks;

import me.bryang.chatlab.authorizer.Authorizer;
import me.bryang.chatlab.user.User;

import javax.inject.Inject;
import java.rmi.server.UID;
import java.util.Map;
import java.util.UUID;

public class IgnoreTask implements Authorizer {
    @Inject
    private Map<String, User> userData;

    @Override
    public boolean denied(String senderUniqueId, String receptorUniqueId) {
        return userData.get(senderUniqueId).containsIgnoredPlayers(UUID.fromString(receptorUniqueId));
    }
}

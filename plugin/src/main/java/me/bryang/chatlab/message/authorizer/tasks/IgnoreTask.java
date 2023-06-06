package me.bryang.chatlab.message.authorizer.tasks;

import me.bryang.chatlab.message.authorizer.Authorizer;
import me.bryang.chatlab.user.User;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class IgnoreTask implements Authorizer {
    @Inject
    private Map<String, User> userData;

    @Override
    public boolean denied(String senderUniqueId, String receptorUniqueId) {

        System.out.println(userData.keySet());
        return userData.get(senderUniqueId).containsIgnoredPlayers(UUID.fromString(receptorUniqueId));
    }
}

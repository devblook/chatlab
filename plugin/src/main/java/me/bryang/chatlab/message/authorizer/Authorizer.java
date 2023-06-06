package me.bryang.chatlab.message.authorizer;

public interface Authorizer {

    boolean denied(String senderUniqueId, String receptorUniqueId);
}

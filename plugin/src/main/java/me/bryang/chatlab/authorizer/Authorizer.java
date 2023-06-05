package me.bryang.chatlab.authorizer;

public interface Authorizer {

    boolean denied(String senderUniqueId, String receptorUniqueId);
}

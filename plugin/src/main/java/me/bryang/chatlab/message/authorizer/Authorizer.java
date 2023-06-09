package me.bryang.chatlab.message.authorizer;

public interface Authorizer {

	default void init(){}
	boolean denied(String senderUniqueId, String receptorUniqueId);
}

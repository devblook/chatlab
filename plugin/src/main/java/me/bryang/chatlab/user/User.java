package me.bryang.chatlab.user;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class User {

	private UUID recentMessenger;
	private boolean privateMessages = true;
	private final Set<String> ignoredPlayers = new HashSet<>();

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

	public boolean privateMessages() {
		return privateMessages;
	}

	public void privateMessages(boolean value) {
		this.privateMessages = value;
	}

	public Set<String> ignoredPlayers() {
		return ignoredPlayers;
	}

	public void ignore(UUID target) {
		ignoredPlayers.add(target.toString());
	}
	public void unIgnore(UUID target) {
		ignoredPlayers.remove(target.toString());
	}

	public boolean containsIgnoredPlayers(UUID target) {
		return ignoredPlayers.contains(target.toString());
	}
}

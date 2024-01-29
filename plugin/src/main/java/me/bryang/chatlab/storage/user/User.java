package me.bryang.chatlab.storage.user;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

	private final String id;

	private UUID recentMessenger;
	private boolean privateMessages = true;
	private List<String> ignoredPlayers = new ArrayList<>();

	public User(String id){
		this.id = id;
	}

	private User(String id, boolean privateMessages, List<String> ignoredPlayers){
		this.id = id;
		this.privateMessages = privateMessages;
		this.ignoredPlayers = ignoredPlayers;
	}
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

	public List<String> ignoredPlayers() {
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

	public String id(){
		return id;
	}

	public static class Builder{

		private String id;

		private boolean privateMessages;
		private List<String> ignoredPlayers = new ArrayList<>();

		public Builder id(String value) {
			this.id = value;
			return this;
		}
		public Builder privateMessages(boolean value){
			this.privateMessages = value;
			return this;
		}

		public Builder ignoredPlayers(List<String> value){
			this.ignoredPlayers = value;
			return this;
		}

		public User build(){
			return new User(id, privateMessages, ignoredPlayers);
		}
	}
}

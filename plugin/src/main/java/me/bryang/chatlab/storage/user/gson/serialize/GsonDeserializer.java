package me.bryang.chatlab.storage.user.gson.serialize;

import com.google.gson.JsonObject;
import me.bryang.chatlab.storage.user.User;
import me.bryang.chatlab.storage.user.gson.codec.GsonReader;

import javax.inject.Singleton;

@Singleton
public class GsonDeserializer {

	public User deserialize(JsonObject jsonObject) {

		GsonReader jsonReader = new GsonReader(jsonObject);

		return new User.Builder()
			.id(jsonReader.getString("id"))
			.privateMessages(jsonReader.getBoolean("private-messages"))
			.ignoredPlayers(jsonReader.getStringList("ignored-players"))
			.build();

	}
}
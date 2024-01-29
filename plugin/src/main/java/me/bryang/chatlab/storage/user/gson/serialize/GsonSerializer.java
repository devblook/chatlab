package me.bryang.chatlab.storage.user.gson.serialize;

import com.google.gson.JsonObject;
import me.bryang.chatlab.storage.user.User;
import me.bryang.chatlab.storage.user.gson.codec.GsonWriter;

import javax.inject.Singleton;

@Singleton
public class GsonSerializer {

	public JsonObject serialize(User user) {
		return new GsonWriter()
			.writePrimitive("id", user.id())
			.writePrimitive("private-messages", user.privateMessages())
			.writeStringList("ignored-players", user.ignoredPlayers())
			.build();
	}
}
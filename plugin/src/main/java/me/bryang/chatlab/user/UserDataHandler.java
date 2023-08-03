package me.bryang.chatlab.user;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import me.bryang.chatlab.user.gson.LocalExclusionStrategy;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Singleton
public class UserDataHandler  {

	@Inject
	@Named("plugin-folder")
	private Path path;
	@Inject
	private Map<String, User> users;
	@Inject
	private Logger logger;

	private Path playersPath;
	private Gson gson;

	public void start() {

		gson = new GsonBuilder()
			.addSerializationExclusionStrategy(new LocalExclusionStrategy())
			.create();
		playersPath = path.resolve("players.json");

		try {
			if (Files.size(playersPath) == 0) {
				logger.info("Data loaded");
				return;
			}


			JsonElement jsonParsed = JsonParser.parseReader(Files.newBufferedReader(playersPath));
			Type type = new TypeToken<Map<String, User>>(){}.getType();
			Map<String, User> newUserData = new Gson().fromJson(jsonParsed, type);

			users.putAll(newUserData);
			logger.info("Data loaded");
		} catch (Exception exception) {
			exception.fillInStackTrace();
		}

	}

	public void update(User user){

		CompletableFuture.runAsync(() -> {

			try (JsonWriter jsonWriter = new JsonWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {
				jsonWriter.jsonValue(gson.toJson(user));

			} catch (Exception exception) {
				exception.fillInStackTrace();
			}
		});
	}

	public void stop() {

		String jsonPath = gson.toJson(users);

		try {
			Files.writeString(playersPath, jsonPath, StandardCharsets.UTF_8);
			logger.info("Data saved");
		} catch (Exception exception) {
			exception.fillInStackTrace();

		}
	}

}


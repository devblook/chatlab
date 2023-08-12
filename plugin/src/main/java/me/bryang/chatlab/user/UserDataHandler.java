package me.bryang.chatlab.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.bryang.chatlab.user.gson.LocalExclusionStrategy;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Singleton
public class UserDataHandler {

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
		playersPath = path.resolve("users");

	}

	public boolean exists(String uniqueId){
		return Files.exists(playersPath.resolve(uniqueId + ".json"));
	}

	public void deserializeAndPut(String uniqueId){

		CompletableFuture.runAsync(() -> {
			String playerData = "";

			try {
				playerData = Files.readString(playersPath.resolve(uniqueId + ".json"));
			} catch (Exception exception) {
				exception.fillInStackTrace();
			}

			User user = gson.fromJson(playerData, User.class);
			users.put(uniqueId, user);
		});
	}

	public void serializeAndSave(String uniqueId, User user){

		CompletableFuture.runAsync(() -> {
			String jsonPath = gson.toJson(user);

			try {
				Files.writeString(playersPath.resolve(uniqueId + ".json"), jsonPath, StandardCharsets.UTF_8);
			} catch (Exception exception) {
				exception.fillInStackTrace();
			}
		});
	}

	public void stop() {

		users.forEach((key, user) -> {
			String jsonPath = gson.toJson(user);

			try {
				Files.writeString(playersPath.resolve(key + ".json"), jsonPath, StandardCharsets.UTF_8);
			}catch (Exception exception){
				exception.fillInStackTrace();
			}
		});

		logger.info("Data saved");
	}
}


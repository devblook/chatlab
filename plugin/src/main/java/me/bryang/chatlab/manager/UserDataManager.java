package me.bryang.chatlab.manager;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import me.bryang.chatlab.manager.gson.LocalExclusionStrategy;
import me.bryang.chatlab.user.User;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.Map;

@Singleton
public class UserDataManager {

	@Inject
	@Named("plugin-folder")
	private Path path;
	@Inject
	private Map<String, User> users;
	@Inject
	private Logger logger;
	private File jsonFile;

	public void init() {

		jsonFile = new File(path.resolve("players.json").toUri());

		try {

			if (!jsonFile.exists()) {
				jsonFile.createNewFile();
				logger.info("Data created");
				return;
			}

			if (jsonFile.length() == 0L) {
				logger.info("Data loaded");
				return;
			}

			JsonElement jsonParsed = JsonParser.parseReader(new FileReader(jsonFile));
			Type type = new TypeToken<Map<String, User>>() {}.getType();
			Map<String, User> newUserData = new Gson().fromJson(jsonParsed, type);
			users.putAll(newUserData);

			logger.info("Data loaded");
		} catch (IOException exception) {
			exception.fillInStackTrace();
		}

	}

	public void stop() {

		Gson gson = new GsonBuilder()
			.addSerializationExclusionStrategy(new LocalExclusionStrategy())
			.create();

		String jsonPath = gson.toJson(users);

		try {

			FileWriter fileWriter = new FileWriter(jsonFile);
			fileWriter.write(jsonPath);
			fileWriter.close();

			logger.info("Data saved");

		} catch (IOException exception) {
			exception.fillInStackTrace();

		}

	}
}

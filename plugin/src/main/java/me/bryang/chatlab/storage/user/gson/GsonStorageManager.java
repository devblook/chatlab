package me.bryang.chatlab.storage.user.gson;

import com.google.gson.JsonObject;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import me.bryang.chatlab.storage.repository.Repository;
import me.bryang.chatlab.storage.user.User;
import me.bryang.chatlab.storage.user.gson.serialize.GsonDeserializer;
import me.bryang.chatlab.storage.user.gson.serialize.GsonSerializer;
import org.slf4j.Logger;
import team.unnamed.inject.InjectAll;
import team.unnamed.inject.InjectIgnore;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@InjectAll
@Singleton
public class GsonStorageManager {

	@Named("users")
	private Repository<User> userRepository;

	@Named("plugin-folder")
	private Path path;

	private Logger logger;

	private GsonSerializer jsonSerializer;
	private GsonDeserializer jsonDeserializer;

	@InjectIgnore
	private Path usersFolder;


	public void init(){

		usersFolder = path.resolve("users");

		if (!Files.exists(usersFolder)) {
			try {
				Files.createDirectory(usersFolder);
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}

		logger.info("Loaded storage system.");
	}

	public boolean exists(String id){
		return Files.exists(usersFolder.resolve(id + ".json"));
	}

	public User deserialize(String id) {
		Path userPath = usersFolder.resolve(id + ".json");

		try (JsonReader jsonReader = new JsonReader(Files.newBufferedReader(userPath))){

			JsonObject jsonObject = TypeAdapters.JSON_ELEMENT.read(jsonReader).getAsJsonObject();
			return jsonDeserializer.deserialize(jsonObject);
		}catch (IOException exception){
			throw new UncheckedIOException(exception);
		}

	}

	public void save(User user){

		Path userPath = usersFolder.resolve(user.id() + ".json");
		JsonObject jsonObject = jsonSerializer.serialize(user);

		try (JsonWriter jsonWriter = new JsonWriter(Files.newBufferedWriter(userPath))){

			jsonWriter.setIndent("  ");
			jsonWriter.setSerializeNulls(false);

			TypeAdapters.JSON_ELEMENT.write(jsonWriter, jsonObject);
		}catch (IOException exception){
			throw new UncheckedIOException(exception);
		}
	}

	public void stop(){

		Collection<User> usersValues = userRepository.findAll();
		usersValues.forEach(this::save);

	}
}
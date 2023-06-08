package me.bryang.chatlab.manager;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.bryang.chatlab.user.User;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

@Singleton
public class UserDataManager {

    @Inject @Named("plugin-folder")
    private Path path;

    @Inject
    private Map<String, User> users;

    @Inject
    private Logger logger;

    private File jsonFile;

    public void init(){

        jsonFile = new File(path.resolve("players.json").toUri());

        try {

            if (!jsonFile.exists()) {
                jsonFile.createNewFile();
                logger.info("Data created");

            }

            JsonElement jsonElement = JsonParser
                    .parseReader(new FileReader(jsonFile));

            if (jsonElement.isJsonNull()){
                return;
            }

            JsonObject jsonObject = jsonElement.getAsJsonObject();

            if (jsonObject == null){
                return;
            }

            jsonObject.getAsJsonObject()
                    .keySet()
                    .forEach(targetUniqueId -> {

                        User user = new User();

                        JsonElement ignoredPlayers = jsonObject.get(targetUniqueId);

                        if (!ignoredPlayers.isJsonNull() && ignoredPlayers.isJsonArray()) {
                            ignoredPlayers.getAsJsonArray().forEach(
                                    element ->
                                    {
                                        user.ignore(element.getAsString());
                                    }
                            );
                        }

                        users.put(targetUniqueId, user);

                    });

            logger.info("Data loaded");
        }catch(IOException exception){
            exception.fillInStackTrace();
        }

    }

    public void stop() {

        JsonObject jsonObject = new JsonObject();

        users.keySet().forEach(userField -> {

            JsonArray jsonArray = new JsonArray();

            User user = users.get(userField);
            user.ignoredPlayers().forEach(jsonArray::add);

            jsonObject.add(userField, jsonArray);


        });

        String jsonString = new Gson().toJson(jsonObject);
        try {

            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(jsonString);
            fileWriter.close();

            logger.info("Data saved");

        }catch(IOException exception){
            exception.fillInStackTrace();

        }

    }
}

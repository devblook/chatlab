package me.bryang.chatlab.manager;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.bryang.chatlab.user.User;
import team.unnamed.inject.InjectAll;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.*;
import java.nio.file.Path;
import java.util.Map;

public class UserDataManager {

    @Inject @Named("plugin-folder")
    private Path path;

    @Inject
    private Map<String, User> users;

    private File jsonFile;
    private JsonObject jsonObject;

    public void init(){

        jsonFile = new File(path.resolve("players.json").toUri());

        try {

            if (!jsonFile.exists()) {
                jsonFile.createNewFile();
                return;
            }

             jsonObject = JsonParser
                    .parseReader(new FileReader(jsonFile))
                    .getAsJsonObject();

            jsonObject
                    .keySet()
                    .forEach(targetUniqueId -> {

                        User user = new User();
                        user.putIgnorePlayers(jsonObject.get(targetUniqueId).getAsJsonObject().keySet());

                        users.put(targetUniqueId, user);

                    });



        }catch(IOException exception){
            exception.fillInStackTrace();
        }

    }

    public void stop() {

        try {

            String jsonPath = new Gson().toJson(users);

            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.write(jsonPath);

        }catch(IOException exception){
            exception.fillInStackTrace();

        }

    }
}

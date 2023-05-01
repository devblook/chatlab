package me.bryang.chatlab;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileCreator {

    private ConfigurationNode configurationNode;
    private YamlConfigurationLoader configLoader;

    private final Path path;
    private final String fileName;

    public FileCreator(Path path, String config) {
        this.path = path;
        this.fileName = config + ".yml";

        try {
            start();
        } catch (ConfigurateException exception) {
            exception.printStackTrace();
        }

    }

    public void start() throws ConfigurateException {

        configLoader = YamlConfigurationLoader
                .builder()
                .path(path
                        .resolve(fileName))
                .build();

        configurationNode = configLoader.load();


    }

    public void reload() {

        try {
            configurationNode = configLoader.load();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public String getString(@NotNull String path) {

        String text = configurationNode
                .node((Object) path.split("\\."))
                .getString();

        if (text == null) {
            return "Error: The path is null.";
        }

        return ChatColor.translateAlternateColorCodes('&', text);
    }


    public List<String> getStringList(@NotNull String path) {

        List<String> text;

        try {
            text = configurationNode
                    .node((Object) path.split("\\."))
                    .getList(String.class, new ArrayList<>());

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

        if (text.isEmpty()) {
            return null;
        }

        text.replaceAll(message -> ChatColor.translateAlternateColorCodes('&', message));
        return text;
    }

    public int getInt(@NotNull String path) {

        return configurationNode
                .node((Object) path.split("\\."))
                .getInt(Integer.MIN_VALUE);
    }


}
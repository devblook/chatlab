package me.bryang.chatlab.manager;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class FileManager extends YamlConfiguration {

    private final String fileName;
    private final Plugin plugin;
    private final File file;

    public FileManager(Plugin plugin, String fileName) {
        this.fileName = fileName + ".yml";
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), fileName + ".yml");
        createFile();
    }


    private void createFile() {
        if (!file.exists()) {
            plugin.saveResource(fileName, false);
        }

        try {
            load(file);
        } catch (InvalidConfigurationException | IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Creation of FileManager '" + fileName + "' failed.", e);
        }
    }

    public void save() {
        try {
            save(file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Save of the file '" + this.fileName + "' failed.", e);
        }
    }

    public void reload() {
        try {
            load(file);
        } catch (IOException | InvalidConfigurationException e) {
            this.plugin.getLogger().log(Level.SEVERE, "Reload of the file '" + this.fileName + "' failed.", e);
        }
    }

    @Override
    public String getString(@NotNull String path) {
        String text = super.getString(path);

        if (text == null) {
            plugin.getLogger().info("Error: Path is null: " + path);
            return "Error - The path is null: " + path;
        }
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    @Override
    public @NotNull List<String> getStringList(@NotNull String path) {
        List<String> text = super.getStringList(path);

        if (text.isEmpty()) {
            plugin.getLogger().info("Error: Path is null: " + path);
            return new ArrayList<>();
        }

        text.replaceAll(message -> ChatColor.translateAlternateColorCodes('&', message));
        return text;
    }
}
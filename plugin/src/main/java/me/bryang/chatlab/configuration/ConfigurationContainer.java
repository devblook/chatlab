package me.bryang.chatlab.configuration;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

public class ConfigurationContainer<T extends ConfigurationSection> {

    private final HoconConfigurationLoader loader;
    private final Class<T> clazz;
    private final AtomicReference<T> internClass;

    public ConfigurationContainer(Class<T> clazz, HoconConfigurationLoader loader , T defaultConfiguration) {
        this.clazz = clazz;
        this.loader = loader;
        this.internClass = new AtomicReference<>(defaultConfiguration);
    }

    public static <T extends ConfigurationSection> ConfigurationContainer<T> create(
            String fileName,
            Path path,
            Class<T> clazz
    ) {
        HoconConfigurationLoader loader = HoconConfigurationLoader
                .builder()
                .path(path.resolve(fileName + ".conf"))
                .defaultOptions(
                        config -> config.header("\n " + fileName + ".conf"))
                .build();

        try {
            CommentedConfigurationNode node = loader.load();
            T defaultConfiguration = node.get(clazz);
            node.set(clazz, defaultConfiguration);
            loader.save(node);
            return new ConfigurationContainer<>(clazz, loader, defaultConfiguration);
        } catch (IOException exception) {
            exception.fillInStackTrace();
            return null;
        }
    }

    public T get() {
        return this.internClass.get();
    }

    public void reload() {
        try {
            CommentedConfigurationNode node = loader.load();
            T newClass = node.get(this.clazz);
            node.set(this.clazz, newClass);
            this.internClass.set(newClass);
            this.loader.save(node);
        } catch (IOException exception) {
            exception.fillInStackTrace();
        }
    }
}

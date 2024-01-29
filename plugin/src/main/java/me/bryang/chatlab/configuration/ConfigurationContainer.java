package me.bryang.chatlab.configuration;

import me.bryang.chatlab.chat.serializer.FormatConfig;
import me.bryang.chatlab.chat.serializer.FormatSerializer;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;

import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;

public class ConfigurationContainer<T extends ConfigurationSection> {

	private HoconConfigurationLoader loader;
	private final Class<T> clazz;
	private AtomicReference<T> internClass;

	public ConfigurationContainer(String fileName, Path path, Class<T> clazz) {

		this.clazz = clazz;
		start(fileName, path);
	}

	public void start(String fileName, Path path) {


		loader = HoconConfigurationLoader
			.builder()
			.path(path.resolve(fileName + ".conf"))
			.defaultOptions(config ->
				config
					.header(String.format("""
		
					%s""", fileName + ".conf"))

					.serializers(serializer -> serializer.register(FormatConfig.class, new FormatSerializer())))
			.build();

		try {
			CommentedConfigurationNode node = loader.load();

			internClass = new AtomicReference<>(node.get(clazz));

			node.set(clazz, internClass.get());
			loader.save(node);


		} catch (IOException exception) {
			exception.fillInStackTrace();
		}
	}

	public T get() {
		return internClass.get();
	}

	public void reload() {

		try {

			CommentedConfigurationNode node = loader.load();

			T newClass = node.get(clazz);
			node.set(clazz, newClass);

			internClass.set(newClass);
			loader.save(node);

		} catch (IOException exception) {
			exception.fillInStackTrace();
		}

    }
}

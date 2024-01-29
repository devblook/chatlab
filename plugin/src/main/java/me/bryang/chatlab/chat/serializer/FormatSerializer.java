package me.bryang.chatlab.chat.serializer;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;

public class FormatSerializer implements TypeSerializer<FormatConfig> {

	@Override
	public FormatConfig deserialize(Type type, ConfigurationNode node) throws SerializationException {

		String condition = node.node("condition").getString();
		String format = node.node("format").getString();

		return new FormatConfig(condition, format);
	}

	@Override
	public void serialize(Type type, @Nullable FormatConfig clazz, ConfigurationNode node) throws SerializationException {

		node.node("condition").set(clazz.condition());
		node.node("format").set(clazz.format());
	}

}

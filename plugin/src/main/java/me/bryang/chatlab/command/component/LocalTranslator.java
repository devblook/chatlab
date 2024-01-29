package me.bryang.chatlab.command.component;

import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.translator.TranslationProvider;
import me.fixeddev.commandflow.translator.Translator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TranslatableComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.function.Function;

@Singleton
public class LocalTranslator implements Translator {

	private final MiniMessage miniMessage = MiniMessage.miniMessage();

	@Inject
	private LocalTranslationProvider localTranslationProvider;

	@Override
	public Component translate(Component component, Namespace namespace) {

		if (!(component instanceof TranslatableComponent translatableComponent)){
			return component;
		}

		String key = translatableComponent.key();
		String translatedMessage = localTranslationProvider.getTranslation(namespace, key);

		if (!translatableComponent.args().isEmpty()){

			String argument = PlainTextComponentSerializer.plainText().serialize(translatableComponent.args().get(0));
			translatedMessage = translatedMessage.replace("<argument>", argument
				.replace("'", ""));
		}

		return miniMessage.deserialize(translatedMessage);

	}


	@Override
	public void setProvider(TranslationProvider provider) {
	}

	@Override
	public void setConverterFunction(Function<String, TextComponent> stringToComponent) {
	}
}

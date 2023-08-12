package me.bryang.chatlab.chat;

import me.bryang.chatlab.chat.condition.Condition;
import me.bryang.chatlab.chat.condition.ConditionType;
import me.bryang.chatlab.chat.condition.type.GroupCondition;
import me.bryang.chatlab.chat.condition.type.PermissionCondition;
import me.bryang.chatlab.chat.serializer.FormatConfig;
import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.RootSection;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class ChatFormatter {

	@Inject
	private ConfigurationContainer<RootSection> configContainer;

	private RootSection.ChatFormat chatSection;
	private final Map<String, String> formats = new HashMap<>();

	private Condition condition;

	public void load(){

		chatSection = configContainer.get().chatFormat;

		switch (chatSection.conditionType){
			case DEFAULT -> {
				return;
			}

			case PERMISSION -> condition = new PermissionCondition();
			case GROUP -> condition = new GroupCondition();
		}

		for (FormatConfig formatConfig : configContainer.get().chatFormat.groupFormats.values()){
			formats.put(formatConfig.condition(), formatConfig.format());
		}

	}

	public String format(Player sender){

		if (chatSection.conditionType == ConditionType.DEFAULT){
			return chatSection.defaultFormat.format;
		}

		String format = condition.getFormat(sender, formats.keySet());

		if (format == null){
			return chatSection.defaultFormat.format;
		}else{
			return formats.get(format);
		}

	}
}

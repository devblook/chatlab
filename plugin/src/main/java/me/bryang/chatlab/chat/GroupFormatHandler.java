package me.bryang.chatlab.chat;

import me.bryang.chatlab.chat.condition.Condition;
import me.bryang.chatlab.chat.condition.ConditionType;
import me.bryang.chatlab.chat.serializer.FormatConfig;
import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.RootSection;
import org.bukkit.entity.Player;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Singleton
public class GroupFormatHandler {

	@Inject
	private ConfigurationContainer<RootSection> configContainer;

	@Inject
	private Map<ConditionType, Condition> conditionMap;

	@Inject
	private Logger logger;
	private RootSection.ChatFormat chatSection;

	private final Map<String, String> formats = new HashMap<>();
	private Condition condition;

	public void load(){

		chatSection = configContainer.get().chatFormat;
		condition = conditionMap.get(chatSection.conditionType);

		if (chatSection.conditionType == ConditionType.DEFAULT){
			return;
		}

		for (FormatConfig formatConfig : configContainer.get().chatFormat.groupFormats.values()){
			formats.put(formatConfig.condition(), formatConfig.format());
		}

	}

	public String format(Player sender){
		String format = condition.getFormat(sender, formats);

		if (chatSection.opFormat.enabled){
			if (sender.isOp() || sender.hasPermission("*")){
				return chatSection.opFormat.format;
			}
		}

		if (format.equalsIgnoreCase("default")){
			return chatSection.defaultFormat.format;
		}else{
			return formats.get(format);
		}
	}
}

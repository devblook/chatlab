package me.bryang.chatlab.chat.condition.type;

import me.bryang.chatlab.chat.condition.Condition;
import org.bukkit.entity.Player;

import java.util.Map;

public class DefaultCondition implements Condition {

	public String getFormat(Player player, Map<String, String> groups) {
		return "default";
	}
}

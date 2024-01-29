package me.bryang.chatlab.chat.condition.type;

import me.bryang.chatlab.chat.condition.Condition;
import org.bukkit.entity.Player;

import java.util.Map;

public class PermissionCondition implements Condition {

	public String getFormat(Player sender, Map<String, String> groups) {

		for (String group : groups.keySet()) {

			if (sender.hasPermission(group)) {
				return group;
			}
		}

		return "default";
	}
}

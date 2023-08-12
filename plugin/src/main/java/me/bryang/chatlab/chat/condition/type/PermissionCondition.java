package me.bryang.chatlab.chat.condition.type;

import me.bryang.chatlab.chat.condition.Condition;
import org.bukkit.entity.Player;

import java.util.Set;

public class PermissionCondition implements Condition {

	@Override
	public String getFormat(Player sender, Set<String> groups) {

		for (String group : groups) {

			if (sender.hasPermission(group)) {
				return group;
			}
		}

		return null;
	}
}

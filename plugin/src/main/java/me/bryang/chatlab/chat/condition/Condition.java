package me.bryang.chatlab.chat.condition;

import org.bukkit.entity.Player;

import java.util.Set;

public interface Condition {

	String getFormat(Player player, Set<String> groups);

}

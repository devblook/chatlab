package me.bryang.chatlab.chat.condition;

import org.bukkit.entity.Player;

import java.util.Map;

public interface Condition {

	String getFormat(Player player, Map<String, String> groups);



}

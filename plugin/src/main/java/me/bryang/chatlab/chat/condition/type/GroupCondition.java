package me.bryang.chatlab.chat.condition.type;

import me.bryang.chatlab.chat.condition.Condition;
import me.bryang.chatlab.hook.type.vault.VaultHook;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.Map;


public class GroupCondition implements Condition {

	@Inject
	private VaultHook vaultHook;

	@Override
	public String getFormat(Player player, Map<String, String> groups) {
        return vaultHook.get().permission().getPrimaryGroup(player);
	}
}

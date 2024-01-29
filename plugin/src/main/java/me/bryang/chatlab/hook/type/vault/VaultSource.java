package me.bryang.chatlab.hook.type.vault;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultSource {

	private Permission permission;

	public String install(){
		RegisteredServiceProvider<Permission> permissionProvider = Bukkit.getServicesManager().getRegistration(Permission.class);

		if (permissionProvider == null){
			return "Err - Unknown provider.";
		}

		if (!permissionProvider.getProvider().hasGroupSupport()){
			return "Err - You need a plugin to support the group format with Vault. Example: LuckPerms";
		}

		permission = permissionProvider.getProvider();
		return "";
	}

	public Permission permission(){
		return permission;
	}

}

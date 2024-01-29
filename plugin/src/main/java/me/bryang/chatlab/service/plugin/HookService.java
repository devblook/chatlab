package me.bryang.chatlab.service.plugin;


import me.bryang.chatlab.hook.HookStatus;
import me.bryang.chatlab.hook.type.vault.VaultHook;
import me.bryang.chatlab.service.Service;
import org.bukkit.Bukkit;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class HookService implements Service {

	@Inject
	private VaultHook vaultHook;

	@Inject
	private Logger logger;

	private final List<String> errorList = new ArrayList<>();

	public void start(){

		HookStatus hookStatus = check();

		switch (hookStatus){
			case SUCCESSFUL -> {
				logger.info("Loaded Vault hook.");
				return;
			}
			case DISABLED -> {
				return;
			}
		}

		if (!errorList.isEmpty()){
			errorList.forEach(errorMessage -> logger.error("Error: " + errorMessage));
		}
	}

	private HookStatus check() {

		if (!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
			return HookStatus.DISABLED;
		}

		HookStatus hookStatus = vaultHook.install();

		if (hookStatus == HookStatus.ERROR) {
			errorList.add(vaultHook.errorMessage());
		}

		return hookStatus;

	}


}

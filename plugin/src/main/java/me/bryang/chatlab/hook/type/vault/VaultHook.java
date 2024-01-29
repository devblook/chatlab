package me.bryang.chatlab.hook.type.vault;

import me.bryang.chatlab.hook.Hook;
import me.bryang.chatlab.hook.HookStatus;

import javax.inject.Singleton;

@Singleton
public class VaultHook implements Hook {

	private final String pluginName = "Vault";
	private String errorMessage = "";
	private VaultSource vaultSource;

	public VaultSource get() {
		return vaultSource;
	}

	@Override
	public HookStatus install() {

		vaultSource = new VaultSource();

		String errorMessage = vaultSource.install();

		if (errorMessage.isEmpty()){
			return HookStatus.SUCCESSFUL;

		}else{
			this.errorMessage = errorMessage;
			return HookStatus.ERROR;
		}
	}

	@Override
	public String errorMessage(){
		return errorMessage;
	}

	@Override
	public String pluginName() {
		return pluginName;
	}

}

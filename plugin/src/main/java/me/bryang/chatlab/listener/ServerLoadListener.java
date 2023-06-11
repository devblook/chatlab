package me.bryang.chatlab.listener;

import me.bryang.chatlab.update.UpdateChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerLoadEvent;

import javax.inject.Inject;

public class ServerLoadListener implements Listener {

	@Inject
	private UpdateChecker updateChecker;

	@EventHandler
	public void onLoad(ServerLoadEvent event){

		if (updateChecker.enabledInAsync()){
			return;
		}

		updateChecker.checkUpdate();
	}

}

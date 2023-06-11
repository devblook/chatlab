package me.bryang.chatlab.update;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.bryang.chatlab.ChatLab;
import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.RootSection;
import org.slf4j.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
public class UpdateChecker {

	@Inject
	private ChatLab plugin;

	@Inject
	private ConfigurationContainer<RootSection> configContainer;

	@Inject
	private Logger logger;

	private boolean asyncFinished;
	private boolean isSent;

	private boolean updated;
	private String lastVersion;
	private UpdateAnnouncementType updateAnnouncementType;
	private final HttpClient httpClient = HttpClient.newHttpClient();


	public void init() {


		RootSection rootSection = configContainer.get();
		AtomicReference<JsonObject> jsonObject = new AtomicReference<>();

		URI uri;
		try {
			uri = new URI("https://api.github.com/repos/DevBlook/ChatLab/releases/latest");

		} catch (URISyntaxException exception) {
			exception.fillInStackTrace();
			return;
		}

		HttpRequest request = HttpRequest
			.newBuilder()
			.uri(uri)
			.header("accept", "application/vnd.github+json")
			.GET()
			.build();


		httpClient
			.sendAsync(request, HttpResponse.BodyHandlers.ofString())
			.thenAccept(action -> {

				JsonElement jsonElement = JsonParser.parseString(action.body());

				jsonObject.set(jsonElement.getAsJsonObject());

				lastVersion = jsonObject.get().get("tag_name").getAsString();
				updated = lastVersion.equalsIgnoreCase(plugin.getPluginMeta().getVersion());

				try {

					updateAnnouncementType = UpdateAnnouncementType.valueOf(
						rootSection.settings.updateAnnouncementType.toUpperCase());

				} catch (IllegalArgumentException illegalArgumentException) {
					updateAnnouncementType = UpdateAnnouncementType.CONSOLE;

				}

				asyncFinished = true;

			});
	}

	public void checkUpdate(){

		if (!asyncFinished) {
			return;
		}

		if (!announcementPresent(UpdateAnnouncementType.CONSOLE)){
			return;
		}

		if (!isUpdated()) {
			logger.info("The plugin has a new update. New version: " + lastVersion());
			logger.info("Download here: https://github.com/devblook/chatlab/releases/latest");

		}
		isSent = true;

	}
	public String lastVersion() {
		return lastVersion;
	}

	public boolean isUpdated() {
		return updated;
	}

	public boolean announcementPresent(UpdateAnnouncementType selectedAnnouncementType) {
		return (selectedAnnouncementType == updateAnnouncementType || updateAnnouncementType == UpdateAnnouncementType.BOTH);
	}
	public boolean enabledInAsync(){
		return isSent;
	}



}

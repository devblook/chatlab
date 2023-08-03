package me.bryang.chatlab;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.bryang.chatlab.configuration.ConfigurationContainer;
import me.bryang.chatlab.configuration.section.RootSection;
import me.bryang.chatlab.message.MessageManager;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Singleton
public class UpdateCheckHandler {

	@Inject @Named("plugin-version")
	private String pluginVersion;
	@Inject
	private ConfigurationContainer<RootSection> configContainer;
	@Inject
	private MessageManager messageManager;

	private boolean updated;
	private boolean requestSuccess;
	private String lastVersion;
	private final HttpClient httpClient = HttpClient.newHttpClient();
	private HttpRequest httpRequest;

	public void init() {

		URI uri;

		try {
			uri = new URI("https://api.github.com/repos/DevBlook/ChatLab/releases/latest");
		} catch (URISyntaxException exception) {
			exception.fillInStackTrace();
			return;
		}

		httpRequest = HttpRequest
			.newBuilder()
			.uri(uri)
			.header("accept", "application/vnd.github+json")
			.GET()
			.build();

	}

	public CompletableFuture<Void> request() {
		return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
			.orTimeout(2, TimeUnit.SECONDS)
			.thenAccept(action -> {

				JsonElement jsonElement = JsonParser.parseString(action.body());
				JsonObject jsonObject = jsonElement.getAsJsonObject();

				lastVersion = jsonObject.get("tag_name").getAsString();
				updated = lastVersion.equalsIgnoreCase(pluginVersion);

				requestSuccess = true;
			});
	}

	public boolean updated() {
		return updated;
	}

	public boolean requestSuccess() {
		return requestSuccess;
	}

	public String lastVersion() {
		return lastVersion;
	}

}


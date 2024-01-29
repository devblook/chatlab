package me.bryang.chatlab.storage.user.gson.codec;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.inject.Singleton;
import java.util.List;
@Singleton
public class GsonWriter {

	private final JsonObject jsonObject;

	public GsonWriter() {
		this.jsonObject = new JsonObject();
	}

	public GsonWriter writePrimitive(String key, String value) {
		jsonObject.addProperty(key, value);
		return this;
	}

	public GsonWriter writePrimitive(String key, Number value) {
		jsonObject.addProperty(key, value);
		return this;
	}

	public GsonWriter writePrimitive(String key, Boolean value) {
		jsonObject.addProperty(key, value);
		return this;
	}

	public GsonWriter writeStringList(String key, List<String> stringList){
		JsonArray jsonArray = new JsonArray();

		for (String value : stringList ){
			jsonArray.add(value);
		}

		jsonObject.add(key, jsonArray);

		return this;
	}

	public JsonObject build(){
		return jsonObject;
	}



}
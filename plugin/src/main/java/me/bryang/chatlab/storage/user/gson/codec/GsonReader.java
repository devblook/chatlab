package me.bryang.chatlab.storage.user.gson.codec;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;


public class GsonReader {

	private final JsonObject jsonObject;

	public GsonReader(JsonObject jsonObject) {
		this.jsonObject = jsonObject;
	}


	public String getString(String key){
		return jsonObject
			.get(key)
			.getAsString();
	}


	public int getInteger(String key){
		return jsonObject
			.get(key)
			.getAsInt();
	}

	public double getDouble(String key){
		return jsonObject
			.get(key)
			.getAsDouble();
	}

	public float getFloat(String key){
		return jsonObject
			.get(key)
			.getAsFloat();
	}

	public boolean getBoolean(String key){
		return jsonObject
			.get(key)
			.getAsBoolean();
	}

	public List<String> getStringList(String key) {

		List<String> stringList = new ArrayList<>();


		jsonObject
			.get(key)
			.getAsJsonArray()
			.forEach(value -> stringList.add(value.getAsString()));

		System.out.println(stringList);
		return stringList;

	}
}

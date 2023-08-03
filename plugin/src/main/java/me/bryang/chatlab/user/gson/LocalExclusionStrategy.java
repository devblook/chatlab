package me.bryang.chatlab.user.gson;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class LocalExclusionStrategy implements ExclusionStrategy {

	@Override
	public boolean shouldSkipField(FieldAttributes fieldAttributes) {
		return fieldAttributes.getAnnotation(GsonIgnore.class) != null;
	}

	@Override
	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

}

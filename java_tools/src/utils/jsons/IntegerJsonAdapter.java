package utils.jsons;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class IntegerJsonAdapter implements JsonDeserializer<Integer>, JsonSerializer<Integer> {
	
	@Override
	public JsonElement serialize(Integer obj, Type type, JsonSerializationContext cont) {
		if (obj== null) return null;
		int data = (Integer)obj;
		return new JsonPrimitive(data);
	}

	@Override
	public Integer deserialize(JsonElement jel, Type type, JsonDeserializationContext cont) throws JsonParseException {
		if (jel == null || jel.isJsonNull() || jel.getAsString().isEmpty()) return null;
		return new Integer(jel.getAsInt());
	}
}
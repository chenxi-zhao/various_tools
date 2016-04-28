package utils.jsons;

import java.lang.reflect.Type;



import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class BooleanJsonAdapter implements JsonDeserializer<Boolean>, JsonSerializer<Boolean> {
	
	

	@Override
	public JsonElement serialize(Boolean arg0, Type arg1, JsonSerializationContext arg2)  {
		
		if (arg0== null) return null;
		Boolean date = (Boolean)arg0;
		boolean b = false;
		if (date == Boolean.TRUE) b = true;
		return new JsonPrimitive(b);
	}

	@Override
	public Boolean deserialize(JsonElement jel, Type type, JsonDeserializationContext cont) throws JsonParseException {
		if (jel == null || jel.isJsonNull() || jel.getAsString().isEmpty()) return null;
		return new Boolean( jel.getAsString());
	}

}

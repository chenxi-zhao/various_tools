package utils.csv;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class BaseTypeJSONAdapterOfBoolean implements JsonDeserializer<Boolean>, JsonSerializer<Boolean> {

	@Override
	public JsonElement serialize(Boolean bool, Type paramType,
			JsonSerializationContext paramJsonSerializationContext) {
		String str = null;
		if(bool != null && bool){
			str = "是";
		}else{
			str = "否";
		}
		return new JsonPrimitive(str);
	}

	@Override
	public Boolean deserialize(JsonElement json, Type paramType,
			JsonDeserializationContext paramJsonDeserializationContext)
			throws JsonParseException {
		Boolean b = false;
		if(json != null){
			String strValue = json.getAsString();
			if(strValue.equals("是") || strValue.equals("true") ||strValue.equals("1")){
				b = true;
			}else{
				b = false;
			}
		}
		return b;
	}

}

package utils.jsons;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateJsonAdapter implements JsonDeserializer<Date>, JsonSerializer<Date>{

	@Override
	public JsonElement serialize(Date arg0, Type arg1, JsonSerializationContext arg2) {
		if (arg0== null) return null;
		Date date = (Date)arg0;
		return new JsonPrimitive(date.toString());
	}

	@Override
	public Date deserialize(JsonElement jel, Type type, JsonDeserializationContext cont) throws JsonParseException {
		if (jel == null || jel.isJsonNull() || jel.getAsString().isEmpty()) return null;
		SimpleDateFormat sdf =new SimpleDateFormat();
		Date d = null;
		if(jel != null){
			String str = jel.getAsString();
			try {
					d = sdf.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return d;
	}
}

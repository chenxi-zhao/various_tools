package utils.csv;

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

public class BaseTypeJSONAdapterOfDate implements JsonDeserializer<Date>, JsonSerializer<Date> {
	//private SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sdf2 =new SimpleDateFormat("yyyy-MM-dd");
	@Override
	public JsonElement serialize(Date date, Type paramType,
			JsonSerializationContext paramJsonSerializationContext) {
		String str = null;
		if(date != null){
			str = sdf2.format(date);
		}else{
			str = "";
		}
		return new JsonPrimitive(str);
	}

	@Override
	public Date deserialize(JsonElement json, Type paramType,
			JsonDeserializationContext paramJsonDeserializationContext)
			throws JsonParseException {
		Date d = null;
		if(json != null){
			String str = json.getAsString();
			try {
					d = sdf2.parse(str);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return d;
	}

}

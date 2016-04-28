package utils.jsons;

import com.google.gson.Gson;

public class JsonToObject {

	public static Object getObjectFromJson(String str,Object object){
		Gson gson = new Gson();
		if(str.substring(0).contains("[")){
			str=str.substring(1, str.length()-1); 
		}
		object = gson.fromJson(str,Object.class);		
		return object;
	}
}

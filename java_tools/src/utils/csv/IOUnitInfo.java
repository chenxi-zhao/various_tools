package utils.csv;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * csv文件头信息类
 *
 */
public class IOUnitInfo {
	
	public static final String defautl_separator = ";";
	
	private static  Gson gson;
	public Gson selfGson;
	
	
	/**
	 * 属性名称（属性名必需和bean属性一致）
	 */
	public String fieldName;
	
	/**
	 * csv头显示名称（在IOUnitGroupInfo中唯一）
	 */
	public String showName;
	
	/**
	 * 属性类型
	 */
	public Class<?> type;
	public Class<?> listObjType;
	/**
	 * 属性值
	 */
	public String value;

	/**
	 * 分割符<B>(数组对象使用)</B>
	 */
	public String separator;
	
	public IOUnitInfo(String fieldName,String showName, Class<?> type){
		this.fieldName = fieldName;
		this.showName = showName;
		this.type = type;
		this.separator = defautl_separator;
		if(gson == null){
			GsonBuilder builder=new GsonBuilder();
			builder.registerTypeAdapter(Date.class, new BaseTypeJSONAdapterOfDate());
			builder.registerTypeAdapter(Boolean.class, new BaseTypeJSONAdapterOfBoolean());
			gson =  builder.create();
		}
	} 
	
	public IOUnitInfo(String fieldName,String showName, Class<?> type, String separator){
		this.fieldName = fieldName;
		this.showName = showName;
		this.type = type;
		this.separator = separator;
		if(gson == null){
			GsonBuilder builder=new GsonBuilder();
			builder.registerTypeAdapter(Date.class, new BaseTypeJSONAdapterOfDate());
			builder.registerTypeAdapter(Boolean.class, new BaseTypeJSONAdapterOfBoolean());
			gson =  builder.create();
		}
	} 
	
	public Gson getGson(){
		if(selfGson != null){
			return selfGson;
		}else{
			return gson;
		}
	}
	
	@Override
	public String toString() {
		return "IOUnit{"+fieldName+","+showName+","+type+"}";
	}

	public String serialize(Object value){
		String result = "";
		if(value == null){
			result = "";
		}
		if(type == String.class){
			String valStr = String.valueOf(value);
			if(valStr.indexOf(",") != -1){
				valStr = "\""+valStr+"\"";
			}
			return String.valueOf(valStr);
		}
		result = getGson().toJson(value);
		if(result == null || result.equals("null")){
			result = "";
		}
		return result;
	}
	
	public Object deserialize(String strValue){
		Object obj = null;
		if(strValue != null && strValue.trim().equals("")){
			strValue = null;
		}
		if(type == String.class){
			obj = strValue;
			return obj;
		}
		//type = Date.class;
		//strValue = "2015/6/18 13:32:00";
		if(strValue != null && type == Date.class){
			if(strValue.contains("/")){
				strValue = strValue.replace("/","-");
			}
		}
				
	/*	if(strValue == null){
			//基础类型null转换会报错
			if(type == long.class
					|| type == int.class
					|| type == short.class
					|| type ==  double.class
					|| type == float.class
					|| type ==  boolean.class){
				return null;
			}
		}*/
		obj = getGson().fromJson(strValue, type);
		return obj;
	}
}




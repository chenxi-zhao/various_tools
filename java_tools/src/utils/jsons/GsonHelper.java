package utils.jsons;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class GsonHelper {
	public static Gson GetJSonBuilder() {
		Map<Class, Object> adapters = new HashMap<Class, Object>();
		buildDefaultAdapter(adapters);
		return GetJSonBuilder(adapters);
	}
	public static Gson GetJSonBuilder(Map<Class, Object> registerAdapters) {
		GsonBuilder builder = new GsonBuilder();
		builder = builder.enableComplexMapKeySerialization() // 支持Map的key为复杂对象的形式
				.serializeNulls().setDateFormat("yyyy-MM-dd")// 时间转化为特定格式
				.setPrettyPrinting() // 对json结果格式化.
				.setVersion(1.0) // 有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.
				.disableHtmlEscaping();
		if (registerAdapters != null && registerAdapters.size() > 0) {
			for (Class clz : registerAdapters.keySet()) {
				builder.registerTypeAdapter(clz, registerAdapters.get(clz));
			}
		}
		Gson gson = builder.create();
		return gson;
	}
	/*
	 * 对象转Json
	 */
	public static String ToJSon(Object obj){
		return GetJSonBuilder().toJson(obj);
	}
	/*
	 * Json转对象
	 */
	

	public static Object FromJSon(String jsonStr, Class clz){
		return GetJSonBuilder().fromJson(jsonStr, clz);
	}
	
	/**
	 * 用于json转数组
	 * @param jsonStr
	 * @param type
	 * @return
	 */
	public static Object FromJSon(String jsonStr, Type type){
		return GetJSonBuilder().fromJson(jsonStr, type);
	}
	
	/*
	 * 注册json转换adapter
	 */
	private static Map<Class, Object> buildDefaultAdapter(Map<Class, Object> registerAdapters) {
		if (registerAdapters == null){
			registerAdapters = new HashMap<Class, Object>();
		}
			
		registerAdapters.put(Date.class, new DateJsonAdapter());
		registerAdapters.put(Boolean.class, new BooleanJsonAdapter());
		registerAdapters.put(Integer.class, new IntegerJsonAdapter());
		registerAdapters.put(Long.class, new LongJsonAdapter());
		
		return registerAdapters;
	}
}

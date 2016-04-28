package utils;

import java.util.Arrays;

import utils.jsons.JsonKit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;

public class ResultMessage {
	
	private static final String DATEFORMATE="yyyy-MM-dd HH:mm:ss.SSS";
	public int code;
	public String message;
	public Object data;

	public String json(){
		return getJson(this.code, this.message, this.data, null);
	}
	
	public String json(String[] keys){
		return getJson(this.code, this.message, this.data, keys);
	}
	
	public String xml() {
		return getXml(this.code, this.message, this.data);
    }
	
	private String getXml(int code,String message,Object data){
		XStream stream=new XStream();
		this.code=code;
		this.message=message;
		this.data=data;
		return stream.toXML(this);
	}
	
	private String getJson(int code, String message, Object data, String[] keys){
		Gson gson;
		if(keys==null || keys.length==0){
			keys=new String[]{"cPassword","cSalt"};
		}else {
			int len=keys.length;
			keys=Arrays.copyOf(keys, len+2);
			keys[len]="cPassword";
			keys[len+1]="cSalt";
		}
		if (keys == null || keys.length == 0) {
			gson = new Gson();
		} else {
				gson = new GsonBuilder().setExclusionStrategies(new JsonKit(keys)).create();
		}
		this.code=code;
		this.message=message;
		this.data=data;
		return gson.toJson(this);
	}
	
	public static String data(Object data, String[] keys) {
		return getJsonResult(200, "操作成功", data, keys,null);
	}

	public static String data(Object data) {
		return data(data,null);
	}
	/**
	 * 对时间日期格式化 并序列化为JSON
	 */
	public static String formatDate(Object data){
		return formatDate(data,DATEFORMATE);
	}
	
	/**
	 * 对时间日期格式化 并序列化为JSON
	 */
	public static String formatDate(Object data,String dateFormat){
		return formatDate(data,dateFormat, null);
	}
	
	/**
	 * 对时间日期格式化 并序列化为JSON
	 */
	public static String formatDate(Object data, String[] keys){
		return formatDate(data,DATEFORMATE, keys);
	}
	
	/**
	 * 
	 * @param data
	 * @param 格式化时间字符串
	 * @param keys
	 * @return
	 */
	public static String formatDate(Object data,String dateFormate, String[] keys){
		return getJsonResult(200, "操作成功", data, keys,dateFormate);
	}

	public static String success() {
		return data(null);
	}
	
	public static String success(String message){
		return getJsonResult(200, message, null, null,null);
	}
	
	public static String error(String message) {
		return getJsonResult(999, message, null, null,null);
	}
	public static String NoAuth(String message) {
		return getJsonResult(901, message, null, null,null);
	}
	
	public static String invalidparameters(){
		return getJsonResult(998, "无效的参数或非法的请求！", null, null, null);
	}
	
	public static String NoLogin(){
		return NoLogin("请登录后操作!");
	}

	public static String NoLogin(String message){
		return getJsonResult(900, message, null, null,null);
	}
	
	/**
	 * 
	 * @param 结果代码
	 * @param 结果信息
	 * @param 结果数据
	 * @param 序列化排序项
	 * @param 是否序列化Lazy的选项
	 * @return 序列化后的json数据
	 */
	public static String getJsonResult(int code, String message, Object data, String[] keys,String dateFormate) {
		ResultMessage result = new ResultMessage();
		result.code = code;
		result.message = message;
		result.data = data;
		Gson gson;
		if(keys==null || keys.length==0){
			keys=new String[]{"cPassword","cSalt"};
		}else {
			int len=keys.length;
			keys=Arrays.copyOf(keys, len+2);
			keys[len]="cPassword";
			keys[len+1]="cSalt";
		}
		GsonBuilder builder=new GsonBuilder();
		if (keys != null && keys.length > 0) {
			builder = builder.setExclusionStrategies(new JsonKit(keys));
		} 
		if(dateFormate!=null && dateFormate!=""){
			builder.setDateFormat(dateFormate);
		}else {
			builder.setDateFormat(DATEFORMATE);
		}
		gson=builder.create();
		return gson.toJson(result);
	}
}

package utils;

/**
 * 字符串工具类
 *
 * @author wues
 *
 * @version 6.0
 * @date 2010-1-6
 */
public class StringUtil {
	/**
	 * <p>
	 * 判断是否是空字符串。
	 * <p>
	 *
	 * @param s
	 * @return
	 */
	public static boolean isNull(String s) {
		if (s == null || s.trim().length() == 0)
			return true;
		return false;
	}

	/**
	 * <p>
	 * 判断是否是空字符串。
	 * <p>
	 *
	 * @param s
	 * @return
	 */
	public static boolean isNotNull(Object o) {
		if (o == null)
			return false;
		else{
			if(toString(o).equals("")){
				return false;
			}

		}
		return true;
	}

	/**
	 * <p>
	 * 判断是否是空字符串。
	 * <p>
	 *
	 * @param s
	 * @return
	 */
	public static boolean isNull(Object o) {
		if (o == null)
			return true;
		else{
			if(toString(o).equals("")){
				return true;
			}

		}
		return false;
	}
	/**
	 *
	 * 将对象转化为字串
	 * @param o
	 * @return
	 * @author chengfei
	 * @since NC6.0
	 * @date 2011-1-6
	 */
	public static String toString(Object o){
		if(o == null){
			return "";
		}
		else if (o instanceof String){

			return ((String) o).trim();
		}
		else{
			return o.toString().trim();
		}
	}
	/**
	 *
	 * 将对象转化为Integer，注意对象为Null时返回-1
	 * @param obj
	 * @return
	 * @author chengfei
	 * @since NC6.0
	 */
	public static Integer toIntegerIgnoreNull(Object obj){
		if(obj == null){
			return Integer.valueOf(-1);
		}
		else if (obj instanceof String){
			return Integer.valueOf(((String) obj).trim());
		}
		else{
			return Integer.valueOf(obj.toString());
		}
	}
	/**
	 * 去除异常信息中的类名
	 * @author chengfei
	 * @time 20110908
	 * @param str
	 * @return
	 */
	public static String filterExceptionFromStr(String str){
		if(isNull(str)){
			return str;
		}
		int pos = str.lastIndexOf("Exception:");
		if(pos > 0)
			return str.substring(pos + 10);
		else
			return str;
	}

	/**
	 * 带null的字符串比较方法
	 * @author zhangrui
	 */
	public static boolean equalWithNull(String str1, String str2) {
		if(str1 == null) {
			return str1 == str2;
		} else {
			return str2 != null && str1.equals(str2);
		}
	}

	/**
	 * 字符数组中是否包含字符串
	 * @author zhangrui
	 */
	public static boolean isArrayContains(String[] strArray, String str) {
		if(!ArrayUtil.isNull(strArray)) {
			for(String strInArr : strArray) {
				if(equalWithNull(strInArr, str)) {
					return true;
				}
			}
		}
		return false;
	}
}
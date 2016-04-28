package utils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;

/**
 * 反射工具类
 *
 */
public class ReflectUtils {

	/**
	 * 得到对象某属性的值
	 * 
	 * @param obj
	 *            对象
	 * @param fieldname
	 *            属性名称
	 * @return
	 */
	public static Object getAttrValue(Object obj, String fieldname) {

		Field field = null;
		String[] fieldNames = fieldname.split("\\.");
		Object currentObj = obj;
		for (int i = 0; i < fieldNames.length; i++) {
			try {
				if (currentObj == null) {
					return null;
				}
				Class<?> c = currentObj.getClass();
				field = c.getDeclaredField(fieldNames[i]);
				field.setAccessible(true);
				currentObj = field.get(currentObj);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return currentObj;
	}

	public static Field getAttrField(Object currentObj, String fn) {

		Field field = null;
		try {
			if (currentObj == null) {
				return null;
			}
			Class<?> c = currentObj.getClass();
			field = c.getDeclaredField(fn);
			field.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return field;
	}

	/**
	 * 设置对象某属性的值
	 * 
	 * @param obj
	 *            对象
	 * @param fieldname
	 *            属性名称
	 * @return
	 */
	public static void setAttrValue(Object obj, String fieldname, Object value) {
		Class<?> c = obj.getClass();
		Field field = null;
		try {
			field = c.getDeclaredField(fieldname);
			field.setAccessible(true);
			field.set(obj, value);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 对象中是否存在属性
	 * 
	 * @param obj
	 * @param fieldname
	 * @return
	 */
	public static boolean isExistField(Object obj, String fieldname) {
		Class<?> c = obj.getClass();
		Field field = null;
		try {
			// field = c.getField(fieldname);
			Field[] fs = c.getDeclaredFields();
			for (Field f : fs) {
				if (f.getName().equals(fieldname)) {
					return true;
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return field != null;
	}

	/**
	 * 对象否存在属性是否是集合
	 * 
	 * @param obj
	 * @param fieldname
	 * @return
	 */
	public static boolean isCollectionField(Object obj, String fieldname) {
		Class<?> c = obj.getClass();
		Field field = null;
		boolean b = false;
		try {
			field = c.getDeclaredField(fieldname);
			b = Collection.class.isAssignableFrom(field.getType());
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return b;
	}

	public static boolean isBaseDatatype(Class<?> c) {
		return c == Boolean.class || c == boolean.class || c == Integer.class || c == int.class || c == Long.class
				|| c == long.class || c == Date.class || c == String.class || Number.class.isAssignableFrom(c);

	}

	public static void attrCopy(Class<?> c, Object tarObj, Object sourceObj) {
		Field[] fs = c.getFields();
		Object obj = null;
		for (Field f : fs) {
			try {
				obj = f.get(sourceObj);
				f.set(tarObj, obj);
			} catch (IllegalArgumentException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}

		}
	}

}

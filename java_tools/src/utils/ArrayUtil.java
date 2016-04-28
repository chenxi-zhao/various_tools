/*
 * @(#)ArrayUtil.java 2009-5-12
 * Copyright 2009 UFIDA Software CO.LTD. All rights reserved.
 */
package utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 数组相关的工具类
 * <p>
 * 
 * @author wangxy
 * @version 1.0 2009-5-12
 * @since NC6.0
 */
public class ArrayUtil {

	public static final String NUMBER = "NUMBER";
	public static final String STRING = "STRING";

	/**
	 * 将一个数组添加到一个List中
	 * 
	 * @param <T>
	 * @param <S>
	 * @param lst
	 * @param arr
	 * @return
	 * @author wangxy
	 * @since NC6.0
	 */
	public static <T, S extends T> List<T> addArrayToList(List<T> lst, S[] arr) {
		if (lst != null && arr != null) {
			for (int i = 0; i < arr.length; i++) {
				lst.add(arr[i]);
			}
		}
		return lst;
	}

	/**
	 * 将一个数组添加到一个List中
	 * 
	 * @param <T>
	 * @param <S>
	 * @param lsta
	 * @param lstb
	 * @return
	 * @author wangxy
	 * @since NC6.0
	 */
	public static <T, S extends T> List<T> addListToList(List<T> lsta,
			List<T> lstb) {
		if (lsta != null && lstb != null) {
			for (int i = 0; i < lstb.size(); i++) {
				lsta.add(lstb.get(i));
			}
		}
		return lsta;
	}

	/**
	 * 将一个新元素加入数组的末尾。
	 * 
	 * @param <T>
	 * @param <S>
	 * @param oldData
	 * @param o
	 * @return
	 * @author wangxy
	 * @since NC6.0
	 */
	public static <T, S extends T> T[] arrayAdd(T[] arr, S obj) {
		T[] newData;
		newData = ArrayUtil.arrayCapacity(arr, 1);
		newData[arr.length] = obj;
		return newData;
	}

	/**
	 * 将新元素加入数组的指定位置。
	 * 
	 * @param <T>
	 * @param <S>
	 * @param arr
	 * @param obj
	 * @param index
	 * @return
	 * @author wangxy
	 * @since NC6.0
	 */
	public static <T, S extends T> T[] arrayAdd(T[] arr, S obj, int index) {
		int size = arr.length;
		if (index > size || index < 0)
			throw new IndexOutOfBoundsException("Index: "
					+ index
					+ ", Size: "
					+ size);

		T[] newData;
		newData = ArrayUtil.arrayCapacity(arr, 1);
		System.arraycopy(newData, index, newData, index + 1, size - index);
		newData[index] = obj;
		return newData;
	}

	/**
	 * 数组容量扩大。 创建日期：(2002-5-30 10:48:40)
	 * 
	 * @param <T>
	 * @param arr
	 * @param increase
	 * @return
	 * @since NC6.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] arrayCapacity(T[] arr, int increase) {
		int oldsize = arr.length;
		int size = oldsize + increase;
		T[] newArr = (T[]) Array.newInstance(arr.getClass().getComponentType(), size);

		System.arraycopy(arr, 0, newArr, 0, oldsize);
		return newArr;
	}
	
	/**
	 * <p>
	 * 获取数组的长度，如果为空，则长度为0。
	 * 
	 * @param obj
	 * @return
	 * @since NC6.0
	 */
	public static int getArrayLength(Object[] obj) {
		return obj == null ? 0 : obj.length;
	}

	/**
	 * <p>
	 * 判断字符串数组内是否有空值。
	 * <p>
	 * 作者：qbh <br>
	 * 日期：2006-9-12
	 * 
	 * @param ss
	 * @return
	 */
	public static boolean hasNull(String[] ss) {
		if (ss == null || ss.length == 0) {
			return true;
		}

		for (int i = 0; i < ss.length; i++) {
			if (StringUtil.isNull(ss[i])) {
				return true;
			}
		}

		return false;
	}

	/**
	 * <p>
	 * 判断是否是空数组。
	 * <p>
	 * 作者：qbh <br>
	 * 日期：2006-1-5
	 * 
	 * @param ss
	 * @return
	 */
	public static boolean isNull(Object[] array) {
		if (array == null || array.length == 0) {
			return true;
		}

		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				return false;
			}
		}

		return true;
	}

	/**
	 * <p>
	 * 判断是否是空数组。
	 * <p>
	 * 作者：liuyga <br>
	 * 日期：2011-6-22
	 * @param <T>
	 * 
	 * @param ss
	 * @return
	 */
	public static <T> boolean isNull(List<T> list) {
		if (list == null || list.size() == 0) {
			return true;
		}

		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) != null) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 合并两个数组，将数组2合并到数组1的后面
	 * 
	 * @param <T>
	 * @param <S>
	 * @param array1
	 * @param array2
	 * @return
	 * @author wangxy
	 * @since NC6.0
	 */
	public static <T, S extends T> T[] mergeArray(T[] array1, S[] array2) {
		if (array1 == null && array2 == null) {
			return null;
		}
		if (isNull(array1)) {
			return array2;
		}
		if (isNull(array2)) {
			return array1;
		}

		// int length = array1.length + array2.length;
		// T[] array = (T[])
		// Array.newInstance(array1.getClass().getComponentType(), length);
		//
		// System.arraycopy(array1, 0, array, 0, array1.length);

		T[] array = ArrayUtil.arrayCapacity(array1, array2.length);
		System.arraycopy(array2, 0, array, array1.length, array2.length);

		return array;
	}

	/**
	 * 将一个数组压入堆栈。
	 * 
	 * @param stk
	 * @param arr
	 * @param isOrder
	 *            <li>true 表示顺序 -- 按数组下标，最小的在栈顶，最大的在栈底 <li>false 表示倒序 --
	 *            最大载栈顶，最小在栈底
	 * @return
	 * @since NC3.5
	 * @see （关联类）
	 */
	public static <T, S extends T> Stack<T> pushArrrayToStack(Stack<T> stk,
			S[] arr, boolean isOrder) {
		if (isOrder) {
			for (int i = arr.length - 1; i >= 0; i--) {
				stk.push(arr[i]);
			}
		} else {
			for (int i = 0; i < arr.length; i++) {
				stk.push(arr[i]);
			}
		}

		return stk;
	}

	/**
	 * 数组收缩，取出空值元素
	 * 
	 * @param <T>
	 * @param arr
	 * @return
	 * @author wangxy
	 * @since NC6.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] shrinkArray(T[] arr) {
		if (arr == null) {
			return null;
		}
		ArrayList<T> lst = new ArrayList<T>();
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] != null) {
				lst.add(arr[i]);
			}
		}
		if (lst.size() == arr.length) {
			return arr;
		}

		T[] shrankArr = arr.getClass() == Object[].class
				? (T[]) new Object[lst.size()]
		        : (T[]) Array.newInstance(arr.getClass().getComponentType(), lst.size());
		return lst.toArray(shrankArr);
	}

	/**
	 * 数组收缩，去除数组的前n个元素
	 * 
	 * @param <T>
	 * @param arr
	 * @param n
	 * @return
	 * @author wangxy
	 * @since NC6.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] shrinkArray(T[] arr, int n) {
		if (arr == null) {
			return null;
		}
		if (n > arr.length) {
			throw new ArrayIndexOutOfBoundsException(" n > arr.length");
		}
		T[] array = (T[]) Array.newInstance(arr.getClass().getComponentType(), arr.length
				- n);
		System.arraycopy(arr, n, array, 0, arr.length - n);
		return array;
	}

	/**
	 * ArrayUtil的构造方法
	 */
	private ArrayUtil() {

	}

	/**
	 * 构造传入参数的字符串数组
	 * 
	 * @param str
	 * @return
	 * @author zhufengg
	 * @since NC6.1
	 */
	public static String[] strArr(String... str) {
		return str;
	}

}
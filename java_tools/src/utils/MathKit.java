package utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MathKit {

	public static double add(double d1, double d2) { // 进行加法运算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.add(b2).doubleValue();
	}

	public static double sub(double d1, double d2) { // 进行减法运算
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.subtract(b2).doubleValue();
	}

	public static double mul(double d1, double d2) { // 进行乘法运算
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.multiply(b2).doubleValue();
	}

	public static double div(double d1, double d2, int len) {// 进行除法运算
		BigDecimal b1 = new BigDecimal(Double.toString(d1));
		BigDecimal b2 = new BigDecimal(Double.toString(d2));
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static double round(double d, int len) { // 进行四舍五入操作

		BigDecimal b1 = new BigDecimal(d);
		BigDecimal b2 = new BigDecimal(1);
		// 任何一个数字除以1都是原数字
		// ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作

		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	
	public static String getPriceFormat(double price) throws Exception{
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("##,##0.00");
		return myformat.format(price);
	}
	
	public static Double formatPrice(Double price) {
		if(price != null) {
			return Double.valueOf(String.format("%.2f", price));
		}
		return null;
	}
}

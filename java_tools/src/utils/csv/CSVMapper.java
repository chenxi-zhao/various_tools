package utils.csv;

/**
 * csv文件导入时行处理类
 *
 * @param <T>
 */
public  interface CSVMapper<T> {
	void run(T t);
}

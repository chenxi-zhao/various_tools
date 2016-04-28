package utils.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import utils.ReflectUtils;


/**
 * CSV文件操作工具类
 *
 */
public class CSVUtils {
	
	public static String  CODEING = "GBK";

	/**
	 * 生成为CVS文件
	 * 
	 * @param exportData      源数据List
	 * @param map                csv文件的列表头map
	 * @param outPutPath       文件路径
	 * @param fileName         文件名称
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public static File createCSVFile(List exportData, Map map, String outPutPath, String fileName, String charsetName) throws Exception {
		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			File file = new File(outPutPath);
			if (!file.exists()) {
				file.mkdir();
			}
			// 定义文件名格式并创建
			csvFile = File.createTempFile(fileName, ".csv", new File(outPutPath));
			System.out.println("【CSVUtils——createCSVFile】csvFile：" + csvFile);
			
			// UTF-8使正确读取分隔符","
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), charsetName), 1024);
			System.out.println("【CSVUtils——createCSVFile】csvFileOutputStream：" + csvFileOutputStream);
			// 写入文件头部
			for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext();) {
				java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
				csvFileOutputStream.write("" + (String) propertyEntry.getValue() != null 
						? (String) propertyEntry.getValue()  
						: "" );
				if (propertyIterator.hasNext()) {
					csvFileOutputStream.write(",");
				}
			}
			csvFileOutputStream.newLine();
			// 写入文件内容
			for (Iterator iterator = exportData.iterator();iterator.hasNext();) {
				Object row = (Object) iterator.next();
				for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator.hasNext();) {
					java.util.Map.Entry propertyEntry =(java.util.Map.Entry) propertyIterator.next();
					Map rowmap = (Map)row;
					String line = null;
					if(rowmap.containsKey(propertyEntry.getKey() )){
						//line = (String)BeanUtils.getProperty(row, (String)propertyEntry.getKey());
						line = (String) rowmap.get(propertyEntry.getKey());
					}
					csvFileOutputStream.write(line == null ? "" : line);
					if (propertyIterator.hasNext()) {
						csvFileOutputStream.write(",");
					}
				}
				if (iterator.hasNext()) {
					csvFileOutputStream.newLine();
				}
			}
			csvFileOutputStream.flush();
		}finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				System.out.println("【CSVUtils——createCSVFile】IOException：" + e.getMessage());
				e.printStackTrace();
			}
		}
		return csvFile;
	}
	
	
	/**
	 * 生成csv文件
	 * @param dataSources  数据源
	 * @param groups          导出文件头信息
	 * @param outPutPath     导出文件路径
	 * @param fileName        导出的文件名称
	 * @param adapters        导出数据源序列化适配器
	 * @return
	 * @throws Exception 
	 */
	public static File createCSVFile(Object dataSources, IOUnitGroupInfo groups,
			String outPutPath, String fileName, String charsetName) throws Exception{
		return createCSVFile( dataSources,  groups,
				 outPutPath,  fileName,null,  charsetName);
	}
	
	public static File createCSVFile(Object dataSources, IOUnitGroupInfo groups,
			String outPutPath, String fileName) throws Exception{
		return createCSVFile( dataSources,  groups,
				 outPutPath,  fileName,null,  CODEING);
	}
	
	public static File createCSVFile(Object dataSources, IOUnitGroupInfo groups,
			String outPutPath, String fileName, boolean excelFileType) throws Exception{
		if(excelFileType){
			return ExcelUtils.createExcelFile(dataSources, groups, outPutPath, fileName);
		}else{
			return createCSVFile( dataSources,  groups,
					 outPutPath,  fileName,null,  CODEING);
		}
	}
	
	//charsetName
	/*public static File createCSVFile(Object dataSources, IOUnitGroupInfo groups,
			String outPutPath, String fileName) throws Exception{
		return createCSVFile( dataSources,  groups,
				 outPutPath,  fileName,null);
	}*/
	
	/**
	 * 生成csv文件
	 * @param dataSources  数据源
	 * @param groups          导出文件头信息
	 * @param outPutPath     导出文件路径
	 * @param fileName        导出的文件名称
	 * @param adapters        导出数据源序列化适配器
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public static File createCSVFile(Object dataSources, IOUnitGroupInfo groups,
			String outPutPath, String fileName, Map<Class<?>,Object> adapters, String charsetName) throws Exception {
		List<Object> objs = null;
		if(dataSources == null){
			System.out.println("【CSVUtils——createCSVFile】dataSources is null" );
			objs = new ArrayList<Object>();
		}
		if(dataSources instanceof Collection){
			objs = (List)dataSources;  
			System.out.println("【CSVUtils——createCSVFile】dataSources is Collenction" );
		}else{
			objs = new ArrayList<Object>();
			System.out.println("【CSVUtils——createCSVFile】dataSources is single Object" );
			objs.add(dataSources);
		}
		Map<String,String> fieldShowNameMap = null;
		fieldShowNameMap = groups.getFieldShowNameMap();
		List<Object> exportData = new ArrayList<Object>();
		Object value = null;
		String strValue = null;
		String showName = null;
		IOUnitInfo unit = null;
		Map<String,Object> row = null;
		Gson gson = null;
		 if(adapters != null){
				GsonBuilder builder=new GsonBuilder();
				for(Class c : adapters.keySet()){
					builder.registerTypeAdapter(c, adapters.get(c));
				}
				gson =  builder.create();
		}
		for(Object category : objs){
			row = new LinkedHashMap<String, Object>();
			for(String key : fieldShowNameMap.keySet()){
				value = ReflectUtils.getAttrValue(category, key);
				showName = fieldShowNameMap.get(key);
				unit = groups.getIOUnitByShowName(showName);
				if(adapters != null){
					unit.selfGson = gson;
				}
				if(value != null){
					if(value.getClass().isArray()){   //处理数组属性
						Object[] vals = (Object[]) value;
						StringBuffer buffer = new StringBuffer();
						int q = 0;
						for(Object o : vals){
							strValue = unit.serialize(value);
							System.out.println("【CSVUtils——createCSVFile】unit.serialize   input value is 【"+value+"】 output value 【"+strValue+"】" );
							buffer.append(strValue);
							if(q != vals.length-1){
								buffer.append(unit.separator);
							}
							q++;
						}
						row.put(key,   strValue);
						System.out.println("【CSVUtils——createCSVFile】row   key  is 【"+key+"】  value 【"+strValue+"】" );
					}else if(Collection.class.isAssignableFrom(value.getClass()) ){  
						System.out.println("【CSVUtils——createCSVFile】value.getClass  is Collection.class" );
						Collection c = (Collection) value;
						Iterator vals = c.iterator();
						StringBuffer buffer = new StringBuffer();
						while(vals.hasNext()){
							 Object item = vals.next();
							if(key.indexOf(".") != -1){
								String newKey = key.substring(key.indexOf(".")+1);
								System.out.println("【CSVUtils——createCSVFile】Iterator.hasNext hasNext is 【"+item+"】 new key is 【"+newKey+"】" );
								item = ReflectUtils.getAttrValue(item, newKey);
								System.out.println("【CSVUtils——createCSVFile】ReflectUtils.getAttrValue item 【"+item+"】" );
							}
							strValue = unit.serialize(item);
							System.out.println("【CSVUtils——createCSVFile】unit.serialize input value is 【"+item+"】 output value is 【"+strValue+"】" );
							if(strValue == null || strValue.trim().equals("")){
								System.out.println("【CSVUtils——createCSVFile】unit.serialize  output value is 【"+strValue+"】and continue" );
								continue;
							}
							buffer.append(strValue);
							buffer.append(unit.separator);
							System.out.println("【CSVUtils——createCSVFile】unit.serialize input value is 【"+item+"】 output value is 【"+strValue+"】" );
						}
						if(buffer.length()>0){
							buffer.deleteCharAt(buffer.lastIndexOf(unit.separator));
							row.put(key,   buffer.toString());
							System.out.println("【CSVUtils——createCSVFile】row   key  is 【"+key+"】  value 【"+buffer.toString()+"】" );
						}
						
					}else{
						strValue = unit.serialize(value);
						row.put(key,   strValue);
						System.out.println("【CSVUtils——createCSVFile】row   key  is 【"+key+"】  value 【"+strValue+"】" );
					}
				}
			}
			exportData.add(row);
		}
		Map<String,String> map = null;
		map = groups.getFieldShowNameMap();
		File file = CSVUtils.createCSVFile(exportData, map, outPutPath, fileName, charsetName);
		return file;
	}

	/**
	 * 导入csv文件处理服务
	 * @param file            //csv文件
	 * @param unitGroup  //导入头信息组
	 * @param mapper     //行处理器
	 * @param t               //行处理类类型
	 */
	public static <T> void readFileDealer(File file, IOUnitGroupInfo unitGroup, 
			CSVMapper<T> mapper, Class<T> t, String charsetName)  throws Exception {
		  readFileDealer( file,  unitGroup, 
				 mapper,  t, null, charsetName);
	}
	
	public static <T> ImportResult readFileDealer(File file, IOUnitGroupInfo unitGroup, 
			CSVMapper<T> mapper, Class<T> t)  throws Exception {
		boolean exportExcel = true;
			if(file != null && file.exists()){
				exportExcel = !file.getName().endsWith(".csv");
			}
		if(exportExcel){
			return ExcelUtils.readFileDealer( file,  unitGroup, 
					 mapper,  t, null, CODEING);
		}else{
			return  readFileDealer( file,  unitGroup, 
					 mapper,  t, null, CODEING);
		}
	}
	
	public static <T> ImportResult readFileDealer(File file, IOUnitGroupInfo unitGroup, 
			CSVMapper<T> mapper, Class<T> t, boolean exportExcel)  throws Exception {
		if(exportExcel){
			return ExcelUtils.readFileDealer( file,  unitGroup, 
					 mapper,  t, null, CODEING);
		}else{
			return readFileDealer( file,  unitGroup, 
					 mapper,  t, null, CODEING);
		}
	}
	
	/**
	 * 导入csv文件处理服务
	 * @param file            //csv文件
	 * @param unitGroup  //导入头信息组
	 * @param mapper     //行处理器
	 * @param t               //行处理类类型
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "resource" })
	public static <T> ImportResult readFileDealer(File file, 
			IOUnitGroupInfo unitGroup,
			CSVMapper<T> mapper, 
			Class<T> t, 
			Map<Class<?>, Object> adapters, 
			String charsetName) throws InstantiationException, IllegalAccessException {
		ImportResult result = new ImportResult();
		InputStream in = null;
		BufferedReader br = null;
		if(charsetName == null){
			charsetName = CODEING;
		}
		try {
			if (file == null) {
				System.out.println("[uorder]【CSVUtils——readFileDealer】文件不存在" );
				throw new RuntimeException("文件不存在");
			}else if(!file.getName().endsWith(".csv")){
				System.out.println("[uorder]【CSVUtils——readFileDealer】文件格式不正确 file name :【"+file.getName()+"】" );
				throw new RuntimeException("文件格式不正确");
			}
			try {
				in = new FileInputStream(file);
				br = new BufferedReader(new InputStreamReader(in, charsetName));
			} catch (FileNotFoundException ef) {
				ef.printStackTrace();
				System.out.println("[uorder]【CSVUtils——readFileDealer】导入文件异常【"+ef.getMessage()+"】" );
				throw new RuntimeException("导入文件异常");
			}catch ( UnsupportedEncodingException e) {
				e.printStackTrace();
				System.out.println("[uorder]【CSVUtils——readFileDealer】导入文件异常【"+e.getMessage()+"】" );
				throw new RuntimeException("导入文件异常");
			}
			Object ioUnit = null;
			List<Object> listUnits = new ArrayList<Object>();
			Gson gson = null;
			if (adapters != null) {
				GsonBuilder builder = new GsonBuilder();
				for (Class<?> c : adapters.keySet()) {
					builder.registerTypeAdapter(c, adapters.get(c));
				}
				gson = builder.create();
			}

			CsvParse c = null;
			c = new CsvParse(br);
			Vector<String> vc = null;
			int ii = 0;
			do {
				try {
					vc = c.getLineContentVector();
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("[uorder]【CSVUtils——readFileDealer】读取行记录异常,行号:【"+ii+"_mesage:"+e.getMessage()+"】" );
					throw new RuntimeException("读取行记录异常,行号:"+ii);
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("[uorder]【CSVUtils——readFileDealer】读取行记录异常,行号:【"+ii+"_mesage:"+e.getMessage()+"】" );
					throw new RuntimeException("读取行记录异常,行号:"+ii);
				}
				if (vc != null) {    //vc内容不为空
					String[] cells = new String[vc.size()];
					cells = vc.toArray(cells);
					if (ii == 0) {   //头信息
						for (String showName : cells) {
							ioUnit = unitGroup.getIOUnitByShowName(showName);
							if (ioUnit != null) {
								listUnits.add(ioUnit);
							} else {
								System.out.println("【CSVUtils——readFileDealer】表头栏目没有对应导入配置:【"+showName+"】" );
								listUnits.add(new Object());
							}
						}
					} else {       //记录信息
						System.out.println("【CSVUtils——readFileDealer】————————第【"+ii+"】条记录开始——————————" );
						T tobj = (T) t.newInstance();
						int j = 0;
						String strVal = null;
						for (Object obj : listUnits) {
							if (!(obj instanceof IOUnitInfo)) {
								j++;
								System.out.println("【CSVUtils——readFileDealer】空表头栏目值:【"+obj+"】" );
								continue;
							}
							if(cells.length <= j){
								j++;
								System.out.println("【CSVUtils——readFileDealer】数据栏数目超出表头:【"+obj+"】" );
								continue;
							}
							strVal = cells[j];
							System.out.println("【CSVUtils——readFileDealer】shell .row .cell.value =【"+strVal+"】" );
							if(strVal == null || strVal.trim().equals("")){
								j++;
								continue;
							}
							if(strVal != null){
								strVal = strVal.trim();
							}
							IOUnitInfo u = (IOUnitInfo) obj;
							Field f = null;
							//-----------------------test start -----------------------------------
							String[] fields = u.fieldName.split("\\.");
							Object currentObj = tobj;
							Class currentClass = null;
							for(int kk = 0; kk < fields.length; kk++){
								//currentClass = currentObj.getClass();
								if(currentClass == null){
									currentClass = currentObj.getClass();
								}
								String fn = fields[kk];
								boolean lastOne = kk == fields.length - 1;
								try {
									f = currentClass.getField(fn);
								} catch (NoSuchFieldException e) {
									e.printStackTrace();
									throw new RuntimeException(t.getName()+"类中找不到属性:"+u.fieldName);
								} catch (SecurityException e) {
									e.printStackTrace();
									throw new RuntimeException(t.getName()+"类中找不到属性:"+u.fieldName);
								}
								Class<?> fieldType = f.getType();
								//设置Gson adapters
								if (adapters != null) {
									u.selfGson = gson;
								}
								if(fieldType.isArray()){   //只支持 两级数组如 在班级对象中 支持 [小组].组名  不支持 [小组].[人员].名
									System.out.println("【CSVUtils——readFileDealer】fieldType isArray" );
									String separator = u.separator;
									StringTokenizer st = new StringTokenizer(strVal, separator);
									System.out.println("【CSVUtils——readFileDealer】StringTokenizer 输入值 【"+strVal+"】" );
									if(lastOne && fields.length == 1){
										Object[] objs = new Object[st.countTokens()];
										int k = 0;
										while(st.hasMoreTokens()){
											String token = st.nextToken();
											Object v = u.deserialize(token);
											System.out.println("【CSVUtils——readFileDealer】u.deserialize  输入值 【"+token+"】output 【"+v+"】" );
											objs[k] = v;
											k++;
										}
										System.out.println("【CSVUtils——readFileDealer】ReflectUtils.setAttrValue 输入值 【"+currentObj+"】" +
												"方法 【"+fn+"】对象【"+objs+"】" );
										ReflectUtils.setAttrValue(currentObj, fn, objs);
									}else if(lastOne && fields.length == 2){
										Object[] ps = (Object[])currentObj;
										int k = 0;
										while(st.hasMoreTokens()){
											String token = st.nextToken();
											Object v = u.deserialize(token);
											System.out.println("【CSVUtils——readFileDealer】u.deserialize  输入值 【"+token+"】output 【"+v+"】" );
											ps[k] = v;
											k++;
										}
										System.out.println("【CSVUtils——readFileDealer】ReflectUtils.setAttrValue 输入值 【"+currentObj+"】" +
												"方法 【"+fn+"】对象【"+ps+"】" );
										ReflectUtils.setAttrValue(currentObj, fn, ps);
									}else {
										Object[] objs = new Object[st.countTokens()];
										System.out.println("【CSVUtils——readFileDealer】ReflectUtils.setAttrValue 输入值 【"+currentObj+"】" +
												"方法 【"+fn+"】对象【"+objs+"】" );
										ReflectUtils.setAttrValue(currentObj, fn, objs);
										currentObj = objs;
										currentClass = u.listObjType;
										//currentClass = currentObj.getClass();
									}
									
								}else if(List.class.isAssignableFrom(fieldType)){
									System.out.println("【CSVUtils——readFileDealer】fieldType List" );
									if(lastOne && fields.length == 1){
										List<Object> objs = new ArrayList<Object>();
										String separator = u.separator;
										System.out.println("【CSVUtils——readFileDealer】lastOne field, StringTokenizer 输入值 【"+separator+"】" );
										StringTokenizer st = new StringTokenizer(strVal, separator);
										while(st.hasMoreTokens()){
											String token = st.nextToken();
											Object v = u.deserialize(token);
											System.out.println("【CSVUtils——readFileDealer】u.deserialize  输入值 【"+token+"】output 【"+v+"】" );
											objs.add(v);
										}
										System.out.println("【CSVUtils——readFileDealer】ReflectUtils.setAttrValue 输入值 【"+currentObj+"】" +
												"属性 【"+u.fieldName+"】对象【"+objs+"】" );
										ReflectUtils.setAttrValue(currentObj, u.fieldName, objs);
									}else if(lastOne && fields.length == 2){
										List ps = (List)currentObj;
										String separator = u.separator;
										System.out.println("【CSVUtils——readFileDealer】StringTokenizer 输入值 【"+separator+"】" );
										StringTokenizer st = new StringTokenizer(strVal, separator);
										while(st.hasMoreTokens()){
											String token = st.nextToken();
											Object v = u.deserialize(token);
											System.out.println("【CSVUtils——readFileDealer】u.deserialize  输入值 【"+token+"】output 【"+v+"】" );
											ps.add(v) ;
										}
										ReflectUtils.setAttrValue(currentObj, fn, ps);
									}else{
										List listObj = new ArrayList();
										System.out.println("【CSVUtils——readFileDealer】ReflectUtils.setAttrValue 输入值 【"+currentObj+"】" +
												"方法 【"+fn+"】对象【"+listObj+"】" );
										ReflectUtils.setAttrValue(currentObj, fn, listObj);
										currentClass = u.listObjType;
										currentObj = listObj;
									}
								}else{
									if(lastOne){
										if(strVal != null){
											if(List.class.isAssignableFrom(currentObj.getClass())){
												String[] newValues = strVal.split(u.separator);
												System.out.println("【CSVUtils——readFileDealer】StringTokenizer 输入值 【"+u.separator+"】" );
												List cList = (List)currentObj;
												for(String nv : newValues){
													Object newObj = currentClass.newInstance();
													Object v = u.deserialize(nv);
													System.out.println("【CSVUtils——readFileDealer】u.deserialize  输入值 【"+nv+"】output 【"+v+"】" );
													ReflectUtils.setAttrValue(newObj, fn, v);
													cList.add(newObj);
												}
											}else{
												Object v = u.deserialize(strVal);
												System.out.println("【CSVUtils——readFileDealer】u.deserialize  输入值 【"+strVal+"】output 【"+v+"】" );
												System.out.println("【CSVUtils——readFileDealer】ReflectUtils.setAttrValue 输入值 【"+currentObj+"】" +
														"方法 【"+fn+"】对象【"+v+"】" );
												ReflectUtils.setAttrValue(currentObj, fn, v);
											}
										}
										//Object v = u.deserialize(strVal);
										//ReflectUtils.setAttrValue(currentObj, fn, v);
									}else{
										Object preObj = ReflectUtils.getAttrValue(currentObj, fn);
										System.out.println("【CSVUtils——readFileDealer】ReflectUtils.getAttrValue 输出值 【"+preObj+"】" +
												"方法 【"+fn+"】对象【"+currentObj+"】" );
										if(preObj == null){
											Field attrField = ReflectUtils.getAttrField(currentObj,fn);
											System.out.println("【CSVUtils——readFileDealer】ReflectUtils.getAttrField 输出值 【"+attrField+"】" +
													"方法 【"+fn+"】对象【"+currentObj+"】" );
											if(attrField != null){
												preObj = attrField.getType().newInstance(); 
											}
											ReflectUtils.setAttrValue(currentObj, fn, preObj);
											System.out.println("【CSVUtils——readFileDealer】ReflectUtils.setAttrValue 输入值 【"+currentObj+"】" +
													"方法 【"+fn+"】对象【"+preObj+"】" );
										}
										currentObj = preObj;
										currentClass = currentObj.getClass();
									}
									
								}
							}
							//-----------------------test end -----------------------------------
							j++;
							
						}
						try{
							result.totalCount++;
							System.out.println("【CSVUtils——readFileDealer】第"+result.totalCount+"条记录———start run———   " );
							mapper.run(tobj);
							System.out.println("【CSVUtils——readFileDealer】第"+result.totalCount+"条记录———start end———  success  " );
							result.successCount++;
						}catch(Exception e){
							String mesage = e.getMessage();
							System.out.println("[uorder]【CSVUtils——readFileDealer】第"+result.totalCount+"条记录———start end———  error 【"+mesage+"】  " );
							result.errorRecords.add(mesage);
							result.errorCount++;
							e.printStackTrace();
						}
					}
					ii++;
				}

			} while (vc != null);
		} finally {
			try {
				if (in != null) {
						in.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				System.out.println("[uorder]【CSVUtils——readFileDealer】【"+e.getMessage()+"】  " );
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 测试数据
	 * 
	 * @param args
	 * @throws Exception 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) throws Exception {
		List exportData = new ArrayList<Map>();
		Map row1 = new LinkedHashMap<String, String>();
		row1.put("1a", "11");
		row1.put("2", "12");
		row1.put("3", "13");
		row1.put("4", "14");
		exportData.add(row1);
		row1 = new LinkedHashMap<String, String>();
		row1.put("1a", "21");
		row1.put("2", "22");
		row1.put("3", "23");
		row1.put("4", "24");
		exportData.add(row1);
		LinkedHashMap map = new LinkedHashMap();
		map.put("1a", "第一列");
		map.put("2", "2");
		map.put("3", "3");
		map.put("4", "4");

		String path = "c:/export/";
		//String path = "export/";
		String fileName = "文件导出";
		File file = CSVUtils.createCSVFile(exportData, map, path,

		fileName,"GBK");
		String fileName2 = file.getName();
		System.out.println("文件名称：" + fileName2);
	}
	

	 public static  List<List<String>> readCSVFile(BufferedReader  br) throws IOException {  
		 	//BufferedReader br = new BufferedReader(fr);  
	        String rec = null;// 一行  
	        String str;// 一个单元格  
	        List<List<String>> listFile = new ArrayList<List<String>>();  
	        try {  
	            // 读取一行  
	            while ((rec = br.readLine()) != null) {  
	                Pattern pCells = Pattern  
	                        .compile("(\"[^\"]*(\"{2})*[^\"]*\")*[^,]*,");  
	                Matcher mCells = pCells.matcher(rec);  
	                List<String> cells = new ArrayList<String>();// 每行记录一个list  
	                // 读取每个单元格  
	                while (mCells.find()) {  
	                    str = mCells.group();  
	                    str = str.replaceAll(  
	                            "(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,", "$1");  
	                    str = str.replaceAll("(?sm)(\"(\"))", "$2");  
	                    cells.add(str);  
	                }  
	                listFile.add(cells);  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            
	            if (br != null) {  
	                br.close();  
	            }  
	        }  
	        return listFile;  
	    } 
	
}
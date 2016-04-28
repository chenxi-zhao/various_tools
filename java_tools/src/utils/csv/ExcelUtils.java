package utils.csv;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
//import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.collections.IteratorUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import utils.ReflectUtils;


/**
 * CSV文件操作工具类

 */
public class ExcelUtils {
	
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
	public static File createExcelFile(List exportData, IOUnitGroupInfo groups, String outPutPath, String fileName, String charsetName) throws Exception {
		Map<String,String> map = null;
		map = groups.getFieldShowNameMap();
		File csvFile = null;
		Workbook wb = null;
		FileOutputStream fileOut = null;
		try {
			 wb = new HSSFWorkbook();
			 CreationHelper createHelper = wb.getCreationHelper();
			 Sheet sheet = wb.createSheet("new sheet");
			 Row row1 = sheet.createRow((short)0);
			 int i = 0;
			// 写入文件头部
			 Object valueObj = null;
			 for (Object key :  map.keySet()) {
				  valueObj = map.get(key);
				  row1.createCell(i++).setCellValue(
					         createHelper.createRichTextString(String.valueOf(valueObj)));
				  System.out.println("【ExcelUtils——createExcelFile】 写入文件头部 value is 【"+valueObj+"】" );
			 }
			 int rowCount = 1;
			 Map row = null;
			// 写入文件内容
			 for (Object rowObj : exportData) {
				 row = (Map)rowObj;
				 row1 = sheet.createRow(rowCount++);
				 int cellCount = 0;
				 for (Object key :  map.keySet()) {
					 if(row.containsKey(key)){
						 valueObj = row.get(key);
						 IOUnitInfo info = groups.getIOUnitByName(String.valueOf(key));
						 if(info.type  == String.class){
							 if(String.valueOf(valueObj).trim().equals("")){
								 row1.createCell(cellCount++).setCellValue(String.valueOf(valueObj));
								 System.out.println("【ExcelUtils——createExcelFile】 写入文件内容 行号【"+rowCount+"】,值 【"+valueObj+"】" );
							 }else{
								 row1.createCell(cellCount++).setCellValue(createHelper.createRichTextString(String.valueOf(valueObj)));
								 System.out.println("【ExcelUtils——createExcelFile】 写入文件内容 行号【"+rowCount+"】,值 【"+valueObj+"】" );
							 }
						 }else{
							 row1.createCell(cellCount++).setCellValue(String.valueOf(valueObj));
							 System.out.println("【ExcelUtils——createExcelFile】 写入文件内容 行号【"+rowCount+"】,值 【"+valueObj+"】" );
						 }
					 }
				 }
				}
			 fileOut = new FileOutputStream(fileName);
			 csvFile = new File(fileName);
		}finally {
			if(fileOut != null){
				wb.write(fileOut);
				fileOut.close();
			}
			// fileOut.close();
			
			if(wb != null){
				wb.close();
			}
		}
		return csvFile;
	}
	
	//合并单元格(导出订单，能够合并单元格)
	@SuppressWarnings("rawtypes")
	public static File createExcelFile(List exportData, IOUnitGroupInfo groups, String outPutPath, String fileName, String charsetName, String mark) throws Exception {
		Map<String,String> map = null;
		map = groups.getFieldShowNameMap();
		File csvFile = null;
		Workbook wb = null;
		FileOutputStream fileOut = null;
		try {
			 wb = new HSSFWorkbook();
			 CreationHelper createHelper = wb.getCreationHelper();
			 Sheet sheet = wb.createSheet("new sheet");
			 CellStyle cs = wb.createCellStyle();
			 cs.setVerticalAlignment(CellStyle.VERTICAL_TOP);
			 Row row1 = sheet.createRow((short)0);
			 int i = 0;
			// 写入文件头部
			 Object valueObj = null;
			 for (Object key :  map.keySet()) {
				  valueObj = map.get(key);
				  row1.createCell(i++).setCellValue(
					         createHelper.createRichTextString(String.valueOf(valueObj)));
				  System.out.println("【ExcelUtils——createExcelFile】 写入文件头部 value is 【"+valueObj+"】" );
			 }
			 int rowCount = 1;
			 Map row = null;
			// 写入文件内容
			 int mergeLine = 0; //计算合并的行数
			 String oldValueOfOrderNo = ""; //保留上一行的订单号，用来和此行比较是不是同一个订单
			 
			 for (Object rowObj : exportData) {
				 row = (Map)rowObj;
				 row1 = sheet.createRow(rowCount++);
				 int cellCount = 0;
				 for (Object key :  map.keySet()) {
					 if(row.containsKey(key)){
						 valueObj = row.get(key);
						 if("cOrderNo".equals(key)){ //是通过订单号来比较是不是同一个订单
							 if( !oldValueOfOrderNo.equals( valueObj) ){
								 oldValueOfOrderNo = String.valueOf(valueObj);
								 mergeLine = 0;
							 }else{
								 mergeLine += 1;
							 } 
						 }
						 IOUnitInfo info = groups.getIOUnitByName(String.valueOf(key));
						 if(info.type  == String.class){
							 if(String.valueOf(valueObj).trim().equals("")){
								 Cell cell = row1.createCell( cellCount++ );
								 cell.setCellStyle(cs);
								 cell.setCellValue(String.valueOf(valueObj));
//								 row1.createCell(cellCount++).setCellValue(String.valueOf(valueObj));
								 System.out.println("【ExcelUtils——createExcelFile】 写入文件内容 行号【"+rowCount+"】,值 【"+valueObj+"】" );
							 }else{
								 Cell cell = row1.createCell( cellCount++ );
								 cell.setCellStyle(cs);
								 cell.setCellValue(String.valueOf(valueObj));
//								 row1.createCell(cellCount++).setCellValue(createHelper.createRichTextString(String.valueOf(valueObj)));
								 System.out.println("【ExcelUtils——createExcelFile】 写入文件内容 行号【"+rowCount+"】,值 【"+valueObj+"】" );
							 }
						 }else{
							 Cell cell = row1.createCell( cellCount++ );
							 cell.setCellStyle(cs);
							 cell.setCellValue(String.valueOf(valueObj));
//							 row1.createCell(cellCount++).setCellValue(String.valueOf(valueObj));
							 System.out.println("【ExcelUtils——createExcelFile】 写入文件内容 行号【"+rowCount+"】,值 【"+valueObj+"】" );
						 }
					 }
				 }
				 if( mergeLine >= 1 ){
					 for(int col = 0; col < 19; col++ ){
						 sheet.addMergedRegion(new CellRangeAddress(rowCount-mergeLine-1, rowCount-1, col, col));
					 }
				 }
			}
			 fileOut = new FileOutputStream(fileName);
			 csvFile = new File(fileName);
		}finally {
			if(fileOut != null){
				wb.write(fileOut);
				fileOut.close();
			}
			// fileOut.close();
			
			if(wb != null){
				wb.close();
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
	public static File createExcelFile(Object dataSources, IOUnitGroupInfo groups,
			String outPutPath, String fileName, String charsetName) throws Exception{
		return createExcelFile( dataSources,  groups,
				 outPutPath,  fileName,null,  charsetName, null);
	}
	
	public static File createExcelFile(Object dataSources, IOUnitGroupInfo groups,
			String outPutPath, String fileName) throws Exception{
		return createExcelFile( dataSources,  groups,
				 outPutPath,  fileName,null,  CODEING, null);
	}
	
	//能够合并mark相同且连续的单元格
	public static File createMergenceExcelFile(Object dataSources, IOUnitGroupInfo groups,
			String outPutPath, String fileName, String mark) throws Exception{
		return createExcelFile( dataSources,  groups,
				 outPutPath,  fileName,null,  CODEING, mark);
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
	public static File createExcelFile(Object dataSources, IOUnitGroupInfo groups,
			String outPutPath, String fileName, Map<Class<?>,Object> adapters, String charsetName, String mark) throws Exception {
		List<Object> objs = null;
		if(dataSources == null){
			System.out.println("【ExcelUtils——createExcelFile】dataSources is null" );
			objs = new ArrayList<Object>();
		}
		if(dataSources instanceof Collection){
			System.out.println("【ExcelUtils——createExcelFile】dataSources is Collenction" );
			objs = (List)dataSources;  
		}else{
			objs = new ArrayList<Object>();
			System.out.println("【ExcelUtils——createExcelFile】single Object" );
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
				if(value != null  && (Collection.class.isAssignableFrom(value.getClass()) &&
						((Collection)value).size() == 0)){
					row.put(key,   "");
					continue;
				}
				if(value != null ){
					if(value.getClass().isArray()){   //处理数组属性
						Object[] vals = (Object[]) value;
						StringBuffer buffer = new StringBuffer();
						int q = 0;
						for(Object o : vals){
							strValue = unit.serialize(value);
							System.out.println("【ExcelUtils——createCSVFile】unit.serialize   input value is 【"+value+"】 output value 【"+strValue+"】" );
							buffer.append(strValue);
							if(q != vals.length-1){
								buffer.append(unit.separator);
							}
							q++;
						}
						row.put(key,   strValue);
						System.out.println("【ExcelUtils——createCSVFile】row   key  is 【"+key+"】  value 【"+strValue+"】" );
					}else if(Collection.class.isAssignableFrom(value.getClass()) ){
						System.out.println("【ExcelUtils——createCSVFile】value.getClass  is Collection.class" );
						Collection c = (Collection) value;
						Iterator vals = c.iterator();
						StringBuffer buffer = new StringBuffer();
						while(vals.hasNext()){
							 Object item = vals.next();
							if(key.indexOf(".") != -1){
								String newKey = key.substring(key.indexOf(".")+1);
								System.out.println("【ExcelUtils——createCSVFile】Iterator.hasNext hasNext is 【"+item+"】 new key is 【"+newKey+"】" );
								item = ReflectUtils.getAttrValue(item, newKey);
								System.out.println("【ExcelUtils——createCSVFile】ReflectUtils.getAttrValue item 【"+item+"】" );
							}
							strValue = unit.serialize(item);
							System.out.println("【ExcelUtils——createCSVFile】unit.serialize input value is 【"+item+"】 output value is 【"+strValue+"】" );
							if(strValue == null || strValue.trim().equals("")){
								System.out.println("【ExcelUtils——createCSVFile】unit.serialize  output value is 【"+strValue+"】and continue" );
								continue;
							}
							buffer.append(strValue);
							buffer.append(unit.separator);
							System.out.println("【ExcelUtils——createCSVFile】unit.serialize input value is 【"+item+"】 output value is 【"+strValue+"】" );
						}
						if(buffer.length()>0){
							buffer.deleteCharAt(buffer.lastIndexOf(unit.separator));
							row.put(key,   buffer.toString());
							System.out.println("【ExcelUtils——createCSVFile】row   key  is 【"+key+"】  value 【"+buffer.toString()+"】" );
						}
						
					}else{
						strValue = unit.serialize(value);
						row.put(key,   strValue);
						System.out.println("【ExcelUtils——createCSVFile】row   key  is 【"+key+"】  value 【"+strValue+"】" );
					}
				}else{
					row.put(key,   "");
				}
			}
			exportData.add(row);
		}
		File file = null;
		if( mark != null || mark != ""){
			file = ExcelUtils.createExcelFile(exportData, groups, outPutPath, fileName, charsetName, mark);  //change some method 
		}else{
			file = ExcelUtils.createExcelFile(exportData, groups, outPutPath, fileName, charsetName);  //change some method
		}
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
		  return readFileDealer( file,  unitGroup, 
				 mapper,  t, null, CODEING);
	}
	
	public static List<Sheet> getExceSheet(File file) {
		Workbook wb = null;
		FileInputStream fis = null;
		 try {
			 	fis = new FileInputStream(file);
				wb = new HSSFWorkbook(fis);
			} catch (Exception e) {
				try {
					wb = new XSSFWorkbook(file);
				} catch (InvalidFormatException e1) {
					System.out.println("[umall]【ExcelUtils——readFileDealer】【"+e.getMessage()+"】  " );
					e1.printStackTrace();
				}  catch (IOException e2) {
					System.out.println("[umall]【ExcelUtils——readFileDealer】【"+e.getMessage()+"】  " );
					e2.printStackTrace();
				} 
			}finally{
				if(fis != null){
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		 int size = wb.getNumberOfSheets();
		 List<Sheet> list = new ArrayList<Sheet>();
		 for(int i = 0; i < size; i++){
			 list.add(wb.getSheetAt(i));
		 }
		 if(wb != null){
			 try {
				wb.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 }
		 return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> ImportResult readFileDealer(Sheet sheet, 
			IOUnitGroupInfo unitGroup,
			CSVMapper<T> mapper, 
			Class<T> t, 
			Map<Class<?>, Object> adapters, 
			String charsetName) throws InstantiationException, IllegalAccessException {
		ImportResult result = new ImportResult();
		if(charsetName == null){
			charsetName = CODEING;
		}
		try {
			if (sheet == null) {
				System.out.println("[umall]【ExcelUtils——readFileDealer】文件不存在" );
				throw new RuntimeException("文件不存在");
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
			Row vc = null;
			int ii = 0;
			Iterator<Row> rows = sheet.rowIterator();
			 //Cell cell = null;
			while(rows.hasNext()) {
				vc =rows.next();
					if (ii == 0) {   //头信息
						try{
						Iterator<Cell> cells = null;
						cells = vc.iterator();
						List<Cell> cellList = null;
						cellList = IteratorUtils.toList(cells);
						for(Cell cell : cellList){
							String showName = cell.getStringCellValue();
							ioUnit = unitGroup.getIOUnitByShowName(showName);
							if (ioUnit != null) {
								//ioUnit = unitGroup.getIOUnitByShowName(showName);
								listUnits.add(ioUnit);
							} else {
								System.out.println("【ExcelUtils——readFileDealer】表头栏目没有对应导入配置:【"+showName+"】" );
								listUnits.add("");
							}
						}
						}catch(Exception eee){
							eee.printStackTrace();
							throw new RuntimeException("读取头信息失败");
						}
						
					} else {       //记录信息
						System.out.println("【ExcelUtils——readFileDealer】————————第【"+ii+"】条记录开始——————————" );
						T tobj = (T) t.newInstance();
						int j = 0;
						String strVal = null;
						//Excel中有某一行是空的时间不执行mapper.run();
						boolean nullRow = true;
						for (Object obj : listUnits) {
							strVal = null;
							if (!(obj instanceof IOUnitInfo)) {
								j++;
								continue;
							}
							/*if(cellList.size() <= j){
								j++;
								continue;
							}*/
							//strVal = cellList.get(j).getStringCellValue();
							if(vc.getCell(j) != null){
								vc.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
								strVal = vc.getCell(j).getStringCellValue();
								if(strVal != null){
									strVal = strVal.trim();
								}
								System.out.println("【ExcelUtils——readFileDealer】【"+j+"】shell .row .cell.value =【"+strVal+"】" );
						     }
							if(strVal == null || strVal.trim().equals("")){
								j++;
								continue;
							}
							if(nullRow){
								nullRow = false;
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
									System.out.println("[umall]【ExcelUtils——readFileDealer】【"+t.getName()+"】类中找不到属性【"+u.fieldName+"】" );
									throw new RuntimeException(t.getName()+"类中找不到属性:"+u.fieldName);
								} catch (SecurityException e) {
									e.printStackTrace();
									System.out.println("[umall]【ExcelUtils——readFileDealer】【"+t.getName()+"】类中找不到属性【"+u.fieldName+"】" );
									throw new RuntimeException(t.getName()+"类中找不到属性:"+u.fieldName);
								}
								Class<?> fieldType = f.getType();
								//设置Gson adapters
								if (adapters != null) {
									u.selfGson = gson;
								}
								if(fieldType.isArray()){   //只支持 两级数组如 在班级对象中 支持 [小组].组名  不支持 [小组].[人员].名
									System.out.println("【ExcelUtils——readFileDealer】fieldType isArray" );
									String separator = u.separator;
									StringTokenizer st = new StringTokenizer(strVal, separator);
									System.out.println("【ExcelUtils——readFileDealer】StringTokenizer 输入值 【"+strVal+"】" );
									if(lastOne && fields.length == 1){
										Object[] objs = new Object[st.countTokens()];
										int k = 0;
										while(st.hasMoreTokens()){
											String token = st.nextToken();
											Object v = u.deserialize(token);
											System.out.println("【ExcelUtils——readFileDealer】u.deserialize  输入值 【"+token+"】output 【"+v+"】" );
											objs[k] = v;
											k++;
										}
										System.out.println("【ExcelUtils——readFileDealer】ReflectUtils.setAttrValue 输入值 【"+currentObj+"】" +
												"属性 【"+fn+"】对象【"+objs+"】" );
										ReflectUtils.setAttrValue(currentObj, fn, objs);
									}else if(lastOne && fields.length == 2){
										Object[] ps = (Object[])currentObj;
										int k = 0;
										while(st.hasMoreTokens()){
											String token = st.nextToken();
											Object v = u.deserialize(token);
											System.out.println("【ExcelUtils——readFileDealer】u.deserialize  输入值 【"+token+"】output 【"+v+"】" );
											ps[k] = v;
											k++;
										}
										System.out.println("【ExcelUtils——readFileDealer】ReflectUtils.setAttrValue 输入值 【"+currentObj+"】" +
												"属性 【"+fn+"】对象【"+ps+"】" );
										ReflectUtils.setAttrValue(currentObj, fn, ps);
									}else {
										Object[] objs = new Object[st.countTokens()];
										System.out.println("【ExcelUtils——readFileDealer】ReflectUtils.setAttrValue 输入值 【"+currentObj+"】" +
												"属性 【"+fn+"】对象【"+objs+"】" );
										ReflectUtils.setAttrValue(currentObj, fn, objs);
										currentObj = objs;
										currentClass = u.listObjType;
										//currentClass = currentObj.getClass();
									}
									
								}else if(List.class.isAssignableFrom(fieldType)){
									System.out.println("【ExcelUtils——readFileDealer】fieldType List" );
									if(lastOne && fields.length == 1){
										List<Object> objs = new ArrayList<Object>();
										String separator = u.separator;
										System.out.println("【ExcelUtils——readFileDealer】lastOne field, StringTokenizer 输入值 【"+separator+"】" );
										StringTokenizer st = new StringTokenizer(strVal, separator);
										while(st.hasMoreTokens()){
											String token = st.nextToken();
											Object v = u.deserialize(token);
											System.out.println("【ExcelUtils——readFileDealer】u.deserialize  输入值 【"+token+"】output 【"+v+"】" );
											objs.add(v);
										}
										System.out.println("【ExcelUtils——readFileDealer】ReflectUtils.setAttrValue 输入值 【"+currentObj+"】" +
												"属性 【"+u.fieldName+"】对象【"+objs+"】" );
										ReflectUtils.setAttrValue(currentObj, u.fieldName, objs);
									}else if(lastOne && fields.length == 2){
										List ps = (List)currentObj;
										String separator = u.separator;
										System.out.println("【ExcelUtils——readFileDealer】StringTokenizer 输入值 【"+separator+"】" );
										StringTokenizer st = new StringTokenizer(strVal, separator);
										while(st.hasMoreTokens()){
											String token = st.nextToken();
											Object v = u.deserialize(token);
											System.out.println("【ExcelUtils——readFileDealer】u.deserialize  输入值 【"+token+"】output 【"+v+"】" );
											ps.add(v) ;
										}
										ReflectUtils.setAttrValue(currentObj, fn, ps);
									}else{
										List listObj = new ArrayList();
										System.out.println("【ExcelUtils——readFileDealer】ReflectUtils.setAttrValue 输入值 【"+currentObj+"】" +
												"属性 【"+fn+"】对象【"+listObj+"】" );
										ReflectUtils.setAttrValue(currentObj, fn, listObj);
										currentClass = u.listObjType;
										currentObj = listObj;
									}
								}else{
									if(lastOne){
										if(strVal != null){
											if(List.class.isAssignableFrom(currentObj.getClass())){
												String[] newValues = strVal.split(u.separator);
												System.out.println("【ExcelUtils——readFileDealer】StringTokenizer 输入值 【"+u.separator+"】" );
												List cList = (List)currentObj;
												for(String nv : newValues){
													Object newObj = currentClass.newInstance();
													Object v = u.deserialize(nv);
													System.out.println("【ExcelUtils——readFileDealer】u.deserialize  输入值 【"+nv+"】output 【"+v+"】" );
													ReflectUtils.setAttrValue(newObj, fn, v);
													cList.add(newObj);
												}
											}else{
												Object v = u.deserialize(strVal);
												System.out.println("【ExcelUtils——readFileDealer】u.deserialize  输入值 【"+strVal+"】output 【"+v+"】" );
												System.out.println("【ExcelUtils——readFileDealer】ReflectUtils.setAttrValue 对象 【"+currentObj+"】" +
														"属性 【"+fn+"】输入值【"+v+"】" );
												ReflectUtils.setAttrValue(currentObj, fn, v);
											}
										}
										//Object v = u.deserialize(strVal);
										//ReflectUtils.setAttrValue(currentObj, fn, v);
									}else{
										Object preObj = ReflectUtils.getAttrValue(currentObj, fn);
										System.out.println("【ExcelUtils——readFileDealer】ReflectUtils.getAttrValue 输出值 【"+preObj+"】" +
												"属性 【"+fn+"】对象【"+currentObj+"】" );
										if(preObj == null){
											Field attrField = ReflectUtils.getAttrField(currentObj,fn);
											System.out.println("【ExcelUtils——readFileDealer】ReflectUtils.getAttrField 输出值 【"+attrField+"】" +
													"属性 【"+fn+"】对象【"+currentObj+"】" );
											if(attrField != null){
												preObj = attrField.getType().newInstance(); 
											}
											ReflectUtils.setAttrValue(currentObj, fn, preObj);
											System.out.println("【ExcelUtils——readFileDealer】ReflectUtils.setAttrValue 输入值 【"+currentObj+"】" +
													"属性 【"+fn+"】对象【"+preObj+"】" );
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
							System.out.println("【ExcelUtils——readFileDealer】第"+result.totalCount+"条记录———start run———   " );
							if(!nullRow){
								mapper.run(tobj);
							}else{
								System.out.println("【ExcelUtils——readFileDealer】第"+result.totalCount+"条记录【是空记录】———start run———   " );
							}
							System.out.println("【ExcelUtils——readFileDealer】第"+result.totalCount+"条记录———start end———  success  " );
							result.successCount++;
						}catch(Exception e){
							String mesage = e.getMessage();
							System.out.println("[uorder]【ExcelUtils——readFileDealer】第"+result.totalCount+"条记录———start end———  error 【"+mesage+"】  " );
							result.errorRecords.add(mesage);
							result.errorCount++;
						}
					}
					ii++;

			};
		}catch (EncryptedDocumentException e) {
			System.out.println("[umall]【ExcelUtils——readFileDealer】【"+e.getMessage()+"】  " );
			e.printStackTrace();
		}  finally {
		}
		return result;
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
	@SuppressWarnings({ })
	public static <T> ImportResult readFileDealer(File file, 
			IOUnitGroupInfo unitGroup,
			CSVMapper<T> mapper, 
			Class<T> t, 
			Map<Class<?>, Object> adapters, 
			String charsetName) throws InstantiationException, IllegalAccessException {
		Workbook wb = null;
		ImportResult result = new ImportResult();
		try{
		if (file == null) {
			System.out.println("[uorder]【ExcelUtils——readFileDealer】文件不存在" );
			throw new RuntimeException("文件不存在");
		}else if(!file.getName().endsWith(".xlsx") && !file.getName().endsWith(".xls")){
			System.out.println("[uorder]【ExcelUtils——readFileDealer】文件格式不正确 file name :【"+file.getName()+"】" );
			throw new RuntimeException("文件格式不正确");
		}
			//in = new FileInputStream(file);
			// wb = WorkbookFactory.create(in);
			FileInputStream fis = null;
			 try {
				 	fis = new FileInputStream(file);
					wb = new HSSFWorkbook(fis);
				} catch (Exception e) {
					wb = new XSSFWorkbook(file);
				}finally{
					if(fis != null){
						try{
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
		    Sheet sheet = wb.getSheetAt(0);
		    result = readFileDealer(sheet, 	 unitGroup,
					 mapper, 
					 t, 
					 adapters, 
					charsetName);
		}catch (FileNotFoundException ef) {
			ef.printStackTrace();
			throw new RuntimeException("导入文件异常");
		}catch ( UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("导入文件异常");
		} catch (EncryptedDocumentException e) {
			System.out.println("[uorder]【ExcelUtils——readFileDealer】【"+e.getMessage()+"】  " );
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			System.out.println("[uorder]【ExcelUtils——readFileDealer】【"+e.getMessage()+"】  " );
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("[uorder]【ExcelUtils——readFileDealer】【"+e.getMessage()+"】  " );
			e.printStackTrace();
		} finally {
			try {
				if(wb != null){
					wb.close();
				}
			} catch (IOException e) {
				System.out.println("[uorder]【ExcelUtils——readFileDealer】【"+e.getMessage()+"】  " );
				e.printStackTrace();
			}
		}
		return result;
	}


	/* public static  List<List<String>> readCSVFile1(BufferedReader  br) throws IOException {  
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
	    } */
	
}
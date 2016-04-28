package utils.csv;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class IOUnitGroupInfo extends ArrayList<IOUnitInfo>{

	private Map<String,String> fsMap = new LinkedHashMap<String,String>();
	private Map<String,String> sfMap = new LinkedHashMap<String,String>();
	private Map<String,IOUnitInfo> fieldUnitMap = new LinkedHashMap<String,IOUnitInfo>();

	/**
	 * 
	 */
	private static final long serialVersionUID = -2095801532180871965L;

	/**
	 * 得到字段名称与显示名称的map
	 * @param ioUnits   
	 * @return
	 */
	public Map<String,String> getFieldShowNameMap(){
		if(fsMap.size() == 0){
			for(IOUnitInfo unit : this){
				fsMap.put(unit.fieldName, unit.showName);
			}	
		}
		return fsMap;
	}
	
	public Map<String,String> getShowNameFieldMap(){
		if(sfMap.size() == 0){
			for(IOUnitInfo unit : this){
				sfMap.put(unit.showName, unit.fieldName);
			}	
		}
		return sfMap;
	}
	
	/**
	 * 根据csv文件的头信息显示名称得到头信息单元对象
	 * @param showName
	 * @return
	 */
	public IOUnitInfo getIOUnitByShowName(String showName){
		Map<String,String>  map = getShowNameFieldMap();
		String field = map.get(showName);
		if(field != null){
			return fieldUnitMap.get(field);
		}
		return null;
	}
	
	public IOUnitInfo getIOUnitByName(String name){
		if(name != null){
			return fieldUnitMap.get(name);
		}
		return null;
	}
	@Override
	public boolean add(IOUnitInfo e) {
		fieldUnitMap.put(e.fieldName, e);
		return super.add(e);
	}
	
	
	
	
}




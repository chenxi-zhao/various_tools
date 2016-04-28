package utils.csv;

import java.util.ArrayList;
import java.util.List;

public class ImportResult {
	public int successCount=0;
	public int totalCount=0;
	public int errorCount=0;
	public List<String> errorRecords=new ArrayList<String>();
	//public String 
	public List<ImportResultMessage> allMsgs=new ArrayList<ImportResultMessage>();
}

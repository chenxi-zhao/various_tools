package utils.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Vector;

/**
 * csv文件处理类
 *
 */
public class CsvParse {
	// 声明读取流
	private BufferedReader inStream = null;
	// 声明返回向量
	private Vector<String> vContent = null;

	public CsvParse(File csvFileName) throws FileNotFoundException {
		 InputStream in = new FileInputStream(csvFileName);
		 try {
			inStream = new BufferedReader(  
				       new InputStreamReader(in, "GBK"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	}
	

	public CsvParse(BufferedReader inStream) {
		this.inStream = inStream;
	}

	public Vector<String> getVContent() {
		return this.vContent;
	}
	/**
	 * 读取文件行对象
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public Vector<String> getLineContentVector() throws IOException, Exception {
		if (this.readCSVNextRecord()) {
			return this.vContent;
		}
		return null;
	}

	public void close() {
		if (inStream != null) {
			try {
				inStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean readCSVNextRecord() throws IOException, Exception {
		// 如果流未被初始化则返回false
		if (inStream == null) {
			return false;
		}
		// 如果结果向量未被初始化，则初始化
		if (vContent == null) {
			vContent = new Vector<>();
		}
		// 移除向量中以前的元素
		vContent.removeAllElements();
		// 声明逻辑行
		String logicLineStr = "";
		// 用于存放读到的行
		StringBuilder strb = new StringBuilder();
		// 声明是否为逻辑行的标志，初始化为false
		boolean isLogicLine = false;
		try {
			while (!isLogicLine) {
				String newLineStr = inStream.readLine();
				if (newLineStr == null) {
					strb = null;
					vContent = null;
					isLogicLine = true;
					break;
				}
				if (newLineStr.startsWith("#")) {
					// 去掉注释
					continue;
				}
				if (!strb.toString().equals("")) {
					strb.append("/r/n");
				}
				strb.append(newLineStr);
				String oldLineStr = strb.toString();
				if (oldLineStr.indexOf(",") == -1) {
					// 如果该行未包含逗号
					if (containsNumber(oldLineStr, "\"") % 2 == 0) {
						// 如果包含偶数个引号
						isLogicLine = true;
						break;
					} else {  //奇数个引号
						if (oldLineStr.startsWith("\"")) {
							if (oldLineStr.equals("\"")) {
								continue;
							} else {   
								String tempOldStr = oldLineStr.substring(1);
								if (isQuoteAdjacent(tempOldStr)) {
									// 如果剩下的引号两两相邻，则不是一行
									continue;
								} else {
									// 否则就是一行
									isLogicLine = true;
									break;
								}
							}
						}
					}
				} else {
					// quotes表示复数的quote
					String tempOldLineStr = oldLineStr.replace("\"\"", "");
					int lastQuoteIndex = tempOldLineStr.lastIndexOf("\"");
					if (lastQuoteIndex == 0) {
						continue;
					} else if (lastQuoteIndex == -1) {
						isLogicLine = true;
						break;
					} else {
						tempOldLineStr = tempOldLineStr.replace("\",\"", "");
						lastQuoteIndex = tempOldLineStr.lastIndexOf("\"");
						if (lastQuoteIndex == 0) {
							continue;
						}
						if (tempOldLineStr.charAt(lastQuoteIndex - 1) == ',') {
							continue;
						} else {
							isLogicLine = true;
							break;
						}
					}
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			// 发生异常时关闭流
			if (inStream != null) {
				inStream.close();
			}
			throw ioe;
		} catch (Exception e) {
			e.printStackTrace();
			// 发生异常时关闭流
			if (inStream != null) {
				inStream.close();
			}
			throw e;
		}
		if (strb == null) {
			// 读到行尾时为返回
			return false;
		}
		// 提取逻辑行
		logicLineStr = strb.toString();
		if (logicLineStr != null) {
			// 拆分逻辑行，把分离出来的原子字符串放入向量中
			while (!logicLineStr.equals("")) {
				String[] ret = readAtomString(logicLineStr);
				String atomString = ret[0];
				logicLineStr = ret[1];
				vContent.add(atomString);
			}
		}
		return true;
	}

	public String[] readAtomString(String lineStr) {
		String atomString = "";// 要读取的原子字符串
		String orgString = "";// 保存第一次读取下一个逗号时的未经任何处理的字符串
		String[] ret = new String[2];// 要返回到外面的数组
		boolean isAtom = false;// 是否是原子字符串的标志
		String[] commaStr = lineStr.split(",");
		while (!isAtom) {
			for (String str : commaStr) {
				if (!atomString.equals("")) {
					atomString = atomString + ",";
				}
				atomString = atomString + str;
				orgString = atomString;
				if (!isQuoteContained(atomString)) {
					// 如果字符串中不包含引号，则为正常，返回
					isAtom = true;
					break;
				} else {
					if (!atomString.startsWith("\"")) {
						// 如果字符串不是以引号开始，则表示不转义，返回
						isAtom = true;
						break;
					} else if (atomString.startsWith("\"")) {
						// 如果字符串以引号开始，则表示转义
						if (containsNumber(atomString, "\"") % 2 == 0) {
							// 如果含有偶数个引号
							String temp = atomString;
							if (temp.endsWith("\"")) {
								temp = temp.replace("\"\"", "");
								if (temp.equals("")) {
									// 如果temp为空
									atomString = "";
									isAtom = true;
									break;
								} else {
									// 如果temp不为空，则去掉前后引号
									temp = temp.substring(1,
											temp.lastIndexOf("\""));
									if (temp.indexOf("\"") > -1) {
										// 去掉前后引号和相邻引号之后，若temp还包含有引号
										// 说明这些引号是单个单个出现的
										temp = atomString;
										temp = temp.substring(1);
										temp = temp.substring(0,
												temp.indexOf("\""))
												+ temp.substring(temp
														.indexOf("\"") + 1);
										atomString = temp;
										isAtom = true;
										break;
									} else {
										// 正常的csv文件
										temp = atomString;
										temp = temp.substring(1,
												temp.lastIndexOf("\""));
										temp = temp.replace("\"\"", "\"");
										atomString = temp;
										isAtom = true;
										break;
									}
								}
							} else {
								// 如果不是以引号结束，则去掉前两个引号
								temp = temp.substring(1, temp.indexOf('\"', 1))
										+ temp.substring(temp.indexOf('\"', 1) + 1);
								atomString = temp;
								isAtom = true;
								break;
							}
						} else {
							// 如果含有奇数个引号
							// TODO 处理奇数个引号的情况
							if (!atomString.equals("\"")) {
								String tempAtomStr = atomString.substring(1);
								if (!isQuoteAdjacent(tempAtomStr)) {
									// 这里做的原因是，如果判断前面的字符串不是原子字符串的时候就读取第一个取到的字符串
									// 后面取到的字符串不计入该原子字符串
									tempAtomStr = atomString.substring(1);
									int tempQutoIndex = tempAtomStr
											.indexOf("\"");
									// 这里既然有奇数个quto，所以第二个quto肯定不是最后一个
									tempAtomStr = tempAtomStr.substring(0,
											tempQutoIndex)
											+ tempAtomStr
													.substring(tempQutoIndex + 1);
									atomString = tempAtomStr;
									isAtom = true;
									break;
								}
							}
						}
					}
				}
			}
		}
		// 先去掉之前读取的原字符串的母字符串
		if (lineStr.length() > orgString.length()) {
			lineStr = lineStr.substring(orgString.length());
		} else {
			lineStr = "";
		}
		// 去掉之后，判断是否以逗号开始，如果以逗号开始则去掉逗号
		if (lineStr.startsWith(",")) {
			if (lineStr.length() > 1) {
				lineStr = lineStr.substring(1);
			} else {
				lineStr = "";
			}
		}
		ret[0] = atomString;
		ret[1] = lineStr;
		return ret;
	}

	public int containsNumber(String parentStr, String parameter) {
		int containNumber = 0;
		if (parentStr == null || parentStr.equals("")) {
			return 0;
		}
		if (parameter == null || parameter.equals("")) {
			return 0;
		}
		for (int i = 0; i < parentStr.length(); i++) {
			i = parentStr.indexOf(parameter, i);
			if (i > -1) {
				i = i + parameter.length();
				i--;
				containNumber = containNumber + 1;
			} else {
				break;
			}
		}
		return containNumber;
	}

	public boolean isQuoteAdjacent(String p_String) {
		boolean ret = false;
		String temp = p_String;
		temp = temp.replace("\"\"", "");
		if (temp.indexOf("\"") == -1) {
			ret = true;
		}
		// TODO 引号相邻
		return ret;
	}

	public boolean isQuoteContained(String p_String) {
		boolean ret = false;
		if (p_String == null || p_String.equals("")) {
			return false;
		}
		if (p_String.indexOf("\"") > -1) {
			ret = true;
		}
		return ret;
	}

	public boolean readCSVFileTitle() throws IOException, Exception {
		String strValue = "";
		boolean isLineEmpty = true;
		do {
			if (!readCSVNextRecord()) {
				return false;
			}
			if (vContent.size() > 0) {
				strValue = (String) vContent.get(0);
			}
			for (Object str : vContent) {
				if (str != null && !str.equals("")) {
					isLineEmpty = false;
					break;
				}
			}
			// csv 文件中前面几行以 # 开头为注释行
		} while (strValue.trim().startsWith("#") || isLineEmpty);
		return true;
	}
}
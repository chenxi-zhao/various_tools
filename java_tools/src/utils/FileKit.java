package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileKit {
	//读文件，返回字符串
    public static String ReadFile(String path){
        File file = new File(path);
        BufferedReader reader = null;
        String laststr = "";
        try {
         //System.out.println("以行为单位读取文件内容，一次读一整行：");
         reader = new BufferedReader(new FileReader(file));
         String tempString = null;
         //int line = 1;
         //一次读入一行，直到读入null为文件结束
         while ((tempString = reader.readLine()) != null) {
          //显示行号
          //System.out.println("line " + line + ": " + tempString);
          laststr = laststr + tempString;
          //line ++;
         }
         reader.close();
        } catch (IOException e) {
         e.printStackTrace();
        } finally {
         if (reader != null) {
          try {
           reader.close();
          } catch (IOException e1) {
          }
         }
        }
        return laststr;
    }
    
	/**
	 * 获取文件扩展名
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileExtension(File file) {
		String fileName = file.getName();
		if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} else {
			return "";
		}
	}

	public static void inputstreamtofile(InputStream ins,File file) {
		  try {
			   OutputStream os = new FileOutputStream(file);
			   int bytesRead = 0;
			   byte[] buffer = new byte[8192];
			   while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				   os.write(buffer, 0, bytesRead);
			   }
			   os.close();
			   ins.close();
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	 }
	 /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}

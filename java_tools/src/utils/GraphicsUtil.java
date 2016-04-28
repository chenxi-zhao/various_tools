package utils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class GraphicsUtil {
	
	// 图片宽和高的最大尺寸
    public static final int IMAGEMAXBIG = 2000;
    
    // 图片宽和高的最小尺寸  
    public static final int IMAGEMINBIG = 10;
    
    // 按原图大小生成新图  
    public static final int CREATENEWIMAGETYPE_0 = 0;
    
    // 按指定的大小生成新图  
    public static final int CREATENEWIMAGETYPE_1 = 1;
    
    // 按原图宽高比例生成新图-按指定的宽度  
    public static final int CREATENEWIMAGETYPE_2 = 2;
    
    // 按原图宽高比例生成新图-按指定的高度  
    public static final int CREATENEWIMAGETYPE_3 = 3;
    
    // 按原图宽高比例生成新图-按指定的宽和高中较大的尺寸  
    public static final int CREATENEWIMAGETYPE_4 = 4;
    
    // 按原图宽高比例生成新图-按指定的宽和高中较小的尺寸  
    public static final int CREATENEWIMAGETYPE_5 = 5;
    
    /** 
     *  
     * @param file 
     *            原图片 
     * @param createType 
     *            处理类型 
     * @param newWidth 
     *            新宽度 
     * @param newHeight 
     *            新高度 
     * @return 
     * @throws Exception 
     */
    public static String createNewImage(File file, int createType, int newWidth,  int newHeight) throws Exception {  
        if (file == null){
            return null;
        }
        
        String fileName = file.getPath();  
        if (fileName == null || "".equals(fileName) || fileName.lastIndexOf(".") == -1){  
            return null;  
        }
        
        String newFileName = "_NEW";
        
        String outFileName = fileName.substring(0, fileName.lastIndexOf("."))  
                + newFileName  
                + fileName.substring(fileName.lastIndexOf("."), fileName.length());
        
        String fileExtName = fileName.substring((fileName.lastIndexOf(".") + 1), fileName.length());  
        if (newWidth < IMAGEMINBIG){
            newWidth = IMAGEMINBIG;
        }else if (newWidth > IMAGEMAXBIG){
            newWidth = IMAGEMAXBIG;
        }
        
        if (newHeight < IMAGEMINBIG){  
            newHeight = IMAGEMINBIG;  
        }else if (newHeight > IMAGEMAXBIG){  
            newHeight = IMAGEMAXBIG;  
        }
        
        // 得到原图信息  
        if (!file.exists() || !file.isAbsolute() || !file.isFile() || !checkImageFile(fileExtName)){  
            return null;
        }
        if ((new File(outFileName)).exists()) {    
            throw new Exception();  
        }
        Image src = ImageIO.read(file);  
        int w = src.getWidth(null);
        int h = src.getHeight(null);
        
        // 确定目标图片的大小  
        int nw = w;
        int nh = h;
        if (createType == CREATENEWIMAGETYPE_0){
        	
        }else if (createType == CREATENEWIMAGETYPE_1) {  
            nw = newWidth;  
            nh = newHeight;  
        } else if (createType == CREATENEWIMAGETYPE_2) {  
            nw = newWidth;  
            nh = (int) ((double) h / (double) w * nw);  
        } else if (createType == CREATENEWIMAGETYPE_3) {  
            nh = newHeight;  
            nw = (int) ((double) w / (double) h * nh);  
        } else if (createType == CREATENEWIMAGETYPE_4) {  
            if ((double) w / (double) h >= (double) newWidth / (double) newHeight) {  
                nh = newHeight;  
                nw = (int) ((double) w / (double) h * nh);  
            } else {  
                nw = newWidth;  
                nh = (int) ((double) h / (double) w * nw);  
            }  
        } else if (createType == CREATENEWIMAGETYPE_5) {  
            if ((double) w / (double) h <= (double) newWidth / (double) newHeight) {  
                nh = newHeight;  
                nw = (int) ((double) w / (double) h * nh);  
            } else {  
                nw = newWidth;  
                nh = (int) ((double) h / (double) w * nw);  
            }  
        }
        
        // 构造目标图片  
        BufferedImage img = new BufferedImage(nw, nh, BufferedImage.TYPE_INT_RGB);  
        
        // 得到目标图片输出流  
        FileOutputStream out = new FileOutputStream(outFileName);
        Graphics2D g2 = img.createGraphics();
        g2.setBackground(Color.WHITE);     
        g2.clearRect(0, 0, nw, nh);  
        g2.drawImage(src, 0, 0, nw, nh, null);
        g2.dispose();
  
        // 将画好的目标图输出到输出流 方式1  
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
        encoder.encode(img);  
        out.close();  
        return outFileName;  
    }  
  
    public static boolean checkImageFile(String extName) {  
        if ("jpg".equalsIgnoreCase(extName))  
            return true;  
        if ("gif".equalsIgnoreCase(extName))  
            return true;  
        if ("bmp".equalsIgnoreCase(extName))  
            return true;  
        if ("jpeg".equalsIgnoreCase(extName))  
            return true;  
        if ("png".equalsIgnoreCase(extName))  
            return true;  
        return false;  
    }  
}

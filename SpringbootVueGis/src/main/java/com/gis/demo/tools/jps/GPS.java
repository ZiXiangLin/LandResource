package com.gis.demo.tools.jps;


import java.io.File;


import com.drew.imaging.ImageMetadataReader;  
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.*;
import com.gis.demo.service.UavService;

/**
 * java读取照片信息
 */
public class GPS{
    public static void main(String[] args) throws Exception, Exception{ 
    	
        String filedir= "C:\\Users\\LinZiXiang\\Desktop\\Dji\\DJI_0046.JPG";
        String fString= "C:\\Users\\LinZiXiang\\Desktop\\SpringbootVueGis\\src\\main\\resources\\META-INF\\resources\\UAV\\DJI_0394.JPG";
//        System.out.println(printImageTags(fString)[1]);
//        double a=GPSgeter.printImageTags(filedir)[0];
        //System.out.println(a);
      //将服务地址存入数据库
		//获取图片的GPS坐标
		//GPSgeter.printImageTags(filePath);
		double latitude = GPSgeter.printImageTags(fString)[0];
		double longitude = GPSgeter.printImageTags(fString)[1];
		System.out.println(latitude);
		//准备sql指令
		//将1个?占位符的实际参数,写在数组中
        UavService uavService = null;
        uavService.insert("DJI_0394.JPG",latitude,longitude);
    } 
    /** 
     * 读取照片里面的信息 
     */ 
	public static double[] printImageTags(String filedir) throws ImageProcessingException, Exception{  
    	double[] gps = new double[]{0.0,0.0};
    	File file = new File(filedir);
    	Metadata metadata = ImageMetadataReader.readMetadata(file);  
        for (Directory directory : metadata.getDirectories()) {  
            for (Tag tag : directory.getTags()) {  
                String tagName = tag.getTagName();  //标签名
                String desc = tag.getDescription(); //标签信息
//                if (tagName.equals("Image Height")) {  
//                    System.out.println("图片高度: "+desc);
//                } else if (tagName.equals("Image Width")) {  
//                    System.out.println("图片宽度: "+desc);
//                } else if (tagName.equals("Date/Time Original")) {  
//                    System.out.println("拍摄时间: "+desc);
//                }else 
               
                if (tagName.equals("GPS Latitude")) {  
                    //System.err.println("纬度(度分秒格式) : "+desc);
                    System.out.println("纬度 : "+pointToLatlong(desc));
                	gps[0]=pointToLatlong(desc);
                } else if (tagName.equals("GPS Longitude")) {  
                    //System.err.println("经度(度分秒格式):  "+desc);
                    System.out.println("经度: "+pointToLatlong(desc));
                	gps[1]=pointToLatlong(desc);
                }
            }  
        }  
        return gps;
        
    }  
    /** 
     * 经纬度格式  转换为  度分秒格式 ,如果需要的话可以调用该方法进行转换
     * @param point 坐标点 
     * @return 
     */ 
    public static Double pointToLatlong (String point ) {  
        Double du = Double.parseDouble(point.substring(0, point.indexOf("°")).trim());  
        Double fen = Double.parseDouble(point.substring(point.indexOf("°")+1, point.indexOf("'")).trim());  
        Double miao = Double.parseDouble(point.substring(point.indexOf("'")+1, point.indexOf("\"")).trim());  
        Double duStr = du + fen / 60 + miao / 60 / 60 ;  
        return duStr;  
    }  
}

package com.gis.demo.tools.jps;

import java.io.File;
import com.gis.demo.service.UavService;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class GPSgeter {

	public static void runsql(String fString) throws ImageProcessingException, Exception{
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
                    //System.out.println("纬度 : "+pointToLatlong(desc));
                	gps[0]=pointToLatlong(desc);
                } else if (tagName.equals("GPS Longitude")) {  
                    //System.err.println("经度(度分秒格式):  "+desc);
                    //System.out.println("经度: "+pointToLatlong(desc));
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

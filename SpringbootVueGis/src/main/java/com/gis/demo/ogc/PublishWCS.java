package com.gis.demo.ogc;



import java.text.SimpleDateFormat;
import java.util.Date;

public class PublishWCS {
	public static String wcs(String imgSource, String imgservicename){
		String pyExePath = "C:\\Python27\\ArcGISx6410.2\\python.exe";
		Operation so = new Operation(pyExePath);
		String pyFilePath = "C:\\Users\\LinZiXiang\\Desktop\\SpringbootVueGis\\Py\\PublishImgService.py";
		String curTime=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		imgservicename = imgservicename +curTime;
		System.out.println("Name: "+imgservicename + " InputData: "+imgSource);
		boolean res = so.AddImageService(pyFilePath, imgservicename, imgSource);
		//boolean res = so.AddImageService(pyFilePath, curTime, imgSource);
		if(res){
			System.out.println("Result: AddImgServiceSuccess,WaitEnableWCS");
			boolean res1 = enwcs("/WCS/"+ imgservicename);
			if(res1){
				System.out.println("Result: EnableSuccess");
				//return imgservicename;
				return "http://localhost:6080/arcgis/services/WCS/" +imgservicename+ "/ImageServer/WCSServer";
			}else{
				System.out.println("Result: EnableFailed");
			}
			System.out.println("-------------------------------");
		}else{
			System.out.println("Result: AddImgServiceFailed");
		}
		System.out.println("-------------------------------");
		return curTime;
	}

	public static boolean enwcs(String serviceNameWithFolder){
		String pyExePath = "C:\\Python27\\ArcGISx6410.2\\python.exe";
		Operation Eo = new Operation(pyExePath);
		
		String pyFileP = "C:\\Users\\LinZiXiang\\Desktop\\SpringbootVueGis\\Py\\PublishImgService.py";
		String host = "localhost";//localhost和6080必须一致
		String port = "6080";//localhost和6080必须一致
		String username = "arcgis";
		String password = "123456";
		//serviceNameWithFolder = "/WCS/FilledDEM";
		String serviceType = "ImageServer";//注意服务类型
		String ogcType = "WCS";//可以开启的OGC服务
		boolean res1 = Eo.EnableOGCServiceCmd(pyFileP, host, port, username, password, serviceNameWithFolder, serviceType, ogcType);
		return res1;
	}

}

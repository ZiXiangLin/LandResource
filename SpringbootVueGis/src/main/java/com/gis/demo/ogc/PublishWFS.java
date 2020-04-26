package com.gis.demo.ogc;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PublishWFS {
	public static void pubwfs(String shpSource, String mapservicename)            {
		//先发布MapServer
		String pyExePath = "C:\\Python27\\ArcGISx6410.2\\python.exe";
		Operation opt = new Operation(pyExePath);
		String createMXDpy = "C:\\Users\\LinZiXiang\\Desktop\\SpringbootVueGis\\Py\\createMXD.py";
		String publishMXDtoservicepy = "C:\\Users\\LinZiXiang\\Desktop\\SpringbootVueGis\\Py\\publishMXDtoservice.py";
		String workspace = "C:\\Users\\LinZiXiang\\Desktop\\OGC";
		//从文件到服务就需要两个文件，shp文件和ags文件
		//shpSource = "E:\\tmp\\rivernet\\test.shp";//WPS的结果数据shp,在GDB中,需传入参数
		String con = "C:\\Users\\LinZiXiang\\Desktop\\OGC\\tmp\\myags.ags";//手动创建的ags文件
		String mxdpath = opt.CreateMXD(createMXDpy, workspace, shpSource);
		System.out.println(mxdpath);
		String curTime=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		//mapservicename =  "geomap" + curTime;//地图服务的名字，可以和WPS结果名一致,需传入参数
		mapservicename =  mapservicename + curTime;
		boolean res = opt.publishMXDtoservic(publishMXDtoservicepy, workspace, mxdpath, con,mapservicename);
		
		//然后开启WFS功能
		if(res){
			System.out.println("Result: AddMapServiceSuccess,WaitEnableWFS");
			boolean res1 = enwfs("/WFS/"+mapservicename);
			
			if(res1){
				System.out.println("Result: EnableSuccess");
			}else{
				System.out.println("Result: EnableFailed");
			}
			System.out.println("-------------------------------");
			
		}else{
			System.out.println("Result: AddMapServiceFailed");
		}
		System.out.println("-------------------------------");
	}
	
	public static boolean enwfs(String serviceNameWithFolder){
		String pyExePath = "C:\\Python27\\ArcGISx6410.2\\python.exe";
		Operation Eo = new Operation(pyExePath);
		
		String pyFileP = "C:\\Users\\LinZiXiang\\Desktop\\SpringbootVueGis\\Py\\EnableService.py";
		String host = "localhost";//localhost和6080必须一致
		String port = "6080";//localhost和6080必须一致
		String username = "arcgis";
		String password = "123456";
		//serviceNameWithFolder = "/WFS/GeoRiver";
		String serviceType = "MapServer";//注意服务类型
		String ogcType = "WFS";//可以开启的OGC服务
		boolean res1 = Eo.EnableOGCServiceCmd(pyFileP, host, port, username, password, serviceNameWithFolder, serviceType, ogcType);
		return res1;
	}

}

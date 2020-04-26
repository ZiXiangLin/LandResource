package com.gis.demo.tools;

import java.util.UUID;

public class CommonUtils {

	//生产uuid方法
	public static String getUUID(){
		
		return UUID.randomUUID().toString();
	}
}

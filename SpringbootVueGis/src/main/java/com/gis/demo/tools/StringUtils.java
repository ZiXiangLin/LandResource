package com.gis.demo.tools;



public class StringUtils {
	
	public static String findId(String result){
		int indexOf = result.indexOf("processid");
		char buf[]=new char[200];
		result.getChars(indexOf+10,indexOf+43,buf,0);
		//System.out.println("id:"+String.valueOf(buf));	
		return String.valueOf(buf);
	}
	
	
	public static String findPartPath(String result){

		int first = result.indexOf("services");
		int last = result.indexOf("GPServer");
		char buf[]=new char[200];
		result.getChars(first+9,last-1,buf,0);
		//System.out.println("path:"+String.valueOf(buf));
		return String.valueOf(buf);
	}
	public static String findResultURL(String resultXML){
		String URL = resultXML.substring(resultXML.indexOf("' href='")+8, resultXML.lastIndexOf("'"));
		//System.out.println(URL);
		return URL;
		
	}
	public static String findwpsname(String wpsurl){
		String wpsname = wpsurl.substring(0,wpsurl.indexOf("/GPServer")).substring((wpsurl.substring(0,wpsurl.indexOf("/GPServer")).lastIndexOf("/"))+1,wpsurl.substring(0,wpsurl.indexOf("/GPServer")).length());
		//System.out.println(URL);
		return wpsname;
		
	}
}

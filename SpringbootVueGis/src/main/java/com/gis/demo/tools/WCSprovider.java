package com.gis.demo.tools;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// 若输入的WCS url是Arcgis server发布的 对WCSurl进行处理
// 通过SAXReader对WCSurl返回的xml文件进行查询，整合返回（String）tif

public class WCSprovider {

	public String getCoverage(String WCSurl) {
		System.out.println("WCS url::::"+WCSurl);
		URL WCSgetcapabilities=null;
		
		try {
			//System.out.println(WCSurl+"?SERVICE=WCS&VERSION=1.1.1&REQUEST=getcapabilities");
			WCSgetcapabilities= new URL(WCSurl+"?SERVICE=WCS&VERSION=1.1.1&REQUEST=getcapabilities");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			System.out.println("not found 404");
		}
		
		SAXReader reader=new SAXReader();
		Map<String, String> map = new HashMap<String, String>();  
        map.put("ows", "http://www.opengis.net/ows/1.1");  
        map.put("wcs", "http://www.opengis.net/wcs/1.1.1");  
        map.put("xsi", "http://www.w3.org/2001/XMLSchema-instance"); 
        reader.getDocumentFactory().setXPathNamespaceURIs(map);
		Document document = null;
		try {
			document = reader.read(WCSgetcapabilities);
			System.out.println("Process (Arcgis Server)WCS getcapabilities: "+ WCSgetcapabilities);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Element rootElement = document.getRootElement();
		ArrayList<String> list=Identifier.getNodes(rootElement,"//ows:Title");
		String WCSIdentifier = list.get(0);

		URL urldescribecoverage=null;
		try {
		urldescribecoverage=new URL(WCSurl+"?SERVICE=WCS&VERSION=1.1.1&REQUEST=describecoverage&identifiers=1");
		} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		System.out.println("not found 404");
		}
		SAXReader reader1=new SAXReader();
		
        reader1.getDocumentFactory().setXPathNamespaceURIs(map);
		Document document1 = null;
		try {
			document1 = reader1.read(urldescribecoverage);
			System.out.println("Process (Arcgis Server)WCS describecoverage: "+ urldescribecoverage);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element rootElement1 = document1.getRootElement();
		ArrayList<String> listcoveragename=Identifier.getNodes(rootElement1,"//ows:Title");
		String coveragename = listcoveragename.get(0);
		
		//ArrayList<String> listCRS =getNodes(rootElement1,"//@crs");
		ArrayList<String> listCRS =Identifier.getNodes(rootElement1,"//wcs:SupportedCRS");
		
		String crs = listCRS.get(1).split(":")[listCRS.get(1).split(":").length-3]+":"+listCRS.get(1).split(":")[listCRS.get(1).split(":").length-1];
		
		ArrayList<String> listBBoxLower=Identifier.getNodes(rootElement1,"//ows:LowerCorner");
		String BBoxLower = listBBoxLower.get(1).replace(" ", ",");
		ArrayList<String> listBBoxUpper=Identifier.getNodes(rootElement1,"//ows:UpperCorner");
		String BBoxUpper = listBBoxUpper.get(1).replace(" ", ",");
		String BBox = BBoxLower+","+BBoxUpper;
		String width = listBBoxUpper.get(0).split(" ")[0];
		String height = listBBoxUpper.get(0).split(" ")[1];
		String Tif=WCSurl+"?request=GetCoverage&amp;service=wcs&amp;version=1.0.0&amp;COVERAGE=1"+"&amp;crs="+crs+"&amp;format=GeoTIFF&amp;BBOX="+BBox+"&amp;width="+width+"&amp;height="+height;
		//String Tif=WCSurl+"?request=GetCoverage&service=wcs&version=1.0.0&COVERAGE=1"+"&crs="+crs+"&format=GeoTIFF&BBOX="+BBox+"&width="+width+"&height="+height;
	    //System.out.println("Tif: "+Tif);
		return Tif;
	}

}

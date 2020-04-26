package com.gis.demo.tools;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Identifier {

	//通过 getCapabilities、DescribeProcess请求获得 wps服务需要的参数
	public static ArrayList<String> getXMLneedIdentifiers(String wpsurl) throws DocumentException {
		
		URL urlgetcapabilities=null;
		try {
			urlgetcapabilities= new URL(wpsurl+"?SERVICE=WPS&VERSIONS=1.0.0&REQUEST=getcapabilities");
			System.out.println("urlgetcabilities: "+urlgetcapabilities);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			System.out.println("not found 404--1");
		}
//		SAXReader reader=new SAXReader();
//		Document document=reader.read(urlgetcapabilities);
		SAXReader reader=new SAXReader();
		Document document=null;
		try {
			document=reader.read(urlgetcapabilities);
			//System.out.println("document1: "+ document1);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		System.out.println("document::::"+document);
		Element rootElement = document.getRootElement();
		
		ArrayList<String> list=getNodes(rootElement,"//ows:Identifier");
		String ProcessIdentifier=null;
		for(int i = 0; i <list.size(); i++) {
			ProcessIdentifier = list.get(i);
		}
		URL urlDescribeProcess=null;
		try {
		urlDescribeProcess=new URL(wpsurl+"?SERVICE=WPS&VERSIONS=1.0.0&REQUEST=DescribeProcess&identifier="+ProcessIdentifier);
		} catch (MalformedURLException e) {
		// TODO Auto-generated catch block
		System.out.println("not found 404--2");
		}
//		SAXReader reader1=new SAXReader();
//		Document document1=reader1.read(urlDescribeProcess);
		SAXReader reader1=new SAXReader();
		Document document1=null;
		try {
			document1=reader1.read(urlDescribeProcess);
			//System.out.println("document1: "+ document1);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("urlDescribeProcess: "+urlDescribeProcess);

		Element rootElement1 = document1.getRootElement();
	
		ArrayList<String> list1=getNodes(rootElement1,"//ows:Identifier");
	
		return list1;
	}

	//查询xml 获得 find_node 结点
	public static  ArrayList<String> getNodes(Element node, String findnode){
		ArrayList<String> list=new ArrayList<String>();
		List<Node> listNode=node.selectNodes(findnode);
		for(Node node1 : listNode) {
			String text=node1.getText();
			list.add(text);
		}
		return list; 
	}  

}
package com.gis.demo.tools;

import java.io.File;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
/**
 * 
 * @author 邓村
 *解析XML
 */
public class ParseXML {
	/**
	 * 
	 * @param url File对象从本地文件中读取
	 * @throws DocumentException
	 */
	public ParseXML(File url) throws DocumentException{
		parse(url);
	}
	/**
	 * 
	 * @param url URL对象
	 * @throws DocumentException
	 */
    public ParseXML(URL url) throws DocumentException{
    	parse(url);
	}
	
	private Document doc = null;

	public Document parse(File url) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(url);
		this.doc = document;
		return document;
	}

	public Document parse(URL url) throws DocumentException {
		SAXReader reader = new SAXReader();
		Map<String, String> xmlMap = new HashMap<>();
		xmlMap.put("gml", "http://www.opengis.net/gml");
		reader.getDocumentFactory().setXPathNamespaceURIs(xmlMap);
		Document document = reader.read(url);
		this.doc = document;
		return document;
	}
	/**
	 * 将坐标点转化为数组样式的字符串,原始数据a b 读为[a,b]
	 * @param str 坐标点字符串
	 * @return 数组样式的字符串
	 */
	public String parsePosListL(String str){
		StringBuffer posList=new StringBuffer("[");
		String[] list=str.split(" ");
		//System.out.println(list.length);
		for(int i=0;i<list.length;i=i+2){
			if(i==list.length-2){
				posList.append("["+list[i]+","+list[i+1]+"]");
			}else{
				posList.append("["+list[i]+","+list[i+1]+"],");
			}	
		}
		posList.append("]");
		return posList.toString();
	}
	/**
	 * 将坐标点转化为数组样式的字符串,原始数据a b 读为[b,a]
	 * @param str 坐标点字符串
	 * @return 数组样式的字符串
	 */
	public String parsePosListR(String str){
		StringBuffer posList=new StringBuffer("[");
		String[] list=str.split(" ");
		//System.out.println(list.length);
		for(int i=0;i<list.length;i=i+2){
			if(i==list.length-2){
				posList.append("["+list[i+1]+","+list[i]+"]");
			}else{
				posList.append("["+list[i+1]+","+list[i]+"],");
			}	
		}
		posList.append("]");
		return posList.toString();
	}
	/**
	 * 获得shape类型
	 * @return
	 */
	public String getType(){
		String type="";
		type=doc.getRootElement().selectNodes("//gml:featureMember").get(0).getName();
		return type;
	}
	/**
	 * 获得空间参考
	 * @return
	 */
	public String getSpatialReference(){
		String spatialReference="";	
		Element el=(Element)doc.getRootElement().selectSingleNode("//gml:Envelope");
		String[] a=el.attributeValue("srsName").split(":");
		spatialReference=a[a.length-1];
		return spatialReference;
	}
	/**
	 * 获得坐标点数据
	 * @return
	 */
	public String getDataL(){	
		StringBuffer bf = new StringBuffer("[");
		List<Node> nodeList=new ArrayList<>();
		nodeList=doc.getRootElement().selectNodes("//gml:featureMember");
		for (int i=0;i<nodeList.size();i++) {
			Node featureMember = nodeList.get(i);	
			String posList=parsePosListL(featureMember.selectSingleNode(".//gml:posList").getText());
			if(i==nodeList.size()-1){
				bf.append(posList);
			}else{
				bf.append(posList+",");
			}	
		}	
		bf.append("]");
		return bf.toString();
	}
	/**
	 * 获得坐标点数据
	 * @return
	 */
	public String getDataR(){	
		StringBuffer bf = new StringBuffer("[");
		List<Node> nodeList=new ArrayList<>();
		nodeList=doc.getRootElement().selectNodes("//gml:featureMember");
		for (int i=0;i<nodeList.size();i++) {
			Node featureMember = nodeList.get(i);	
			String posList=parsePosListR(featureMember.selectSingleNode(".//gml:posList").getText());
			if(i==nodeList.size()-1){
				bf.append(posList);
			}else{
				bf.append(posList+",");
			}	
		}	
		bf.append("]");
		return bf.toString();
	}
	/**
	 * json字符串 L
	 * @return
	 */
	public String toJsonStringL(){
		StringBuffer json=new StringBuffer();
		json.append("{");
		json.append("\"type\":");
		json.append("\""+getType()+"\",");
		json.append("\"data\":");
		json.append("\""+getDataL()+"\",");
		json.append("\"spatialReference\":");
		json.append("\""+getSpatialReference()+"\"");
		json.append("}");
		return json.toString();
	}
	/**
	 * json字符串 R
	 * @return
	 */
	public String toJsonStringR(){
		StringBuffer json=new StringBuffer();
		json.append("{");
		json.append("\"type\":");
		json.append("\""+getType()+"\",");
		json.append("\"data\":");
		json.append("\""+getDataR()+"\",");
		json.append("\"spatialReference\":");
		json.append("\""+getSpatialReference()+"\"");
		json.append("}");
		return json.toString();
	}

}

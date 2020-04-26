package com.gis.demo.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.RandomAccessFile;
/**
 * @author node
 *
 */
public class FileOperation {
	
	/**
	  * 创建文件 
	  * @param fileName 
	  * @return 
	  */  
	public boolean createFile(File fileName)throws Exception{
	  boolean flag=false;  
	  try{  
	   if(!fileName.exists()){  
	    fileName.createNewFile();  
	    flag=true;  
	   }  
	  }catch(Exception e){  
	   e.printStackTrace();  
	  }  
	  return flag;  
	 }   
	   
	 /**
	  * 读TXT文件内容 
	  * @param fileName 
	  * @return 
	  */
	public String readTxtFile(File fileName)throws Exception{
	  String result=null;  
	  FileReader fileReader=null;  
	  BufferedReader bufferedReader=null;  
	  try{  
	   fileReader=new FileReader(fileName);  
	   bufferedReader=new BufferedReader(fileReader);  
	   try{  
	    String read=null;  
	    while((read=bufferedReader.readLine())!=null){  
	     result=read;  
	    }  
	   }catch(Exception e){  
	    e.printStackTrace();  
	   }  
	  }catch(Exception e){  
	   e.printStackTrace();  
	  }finally{  
	   if(bufferedReader!=null){  
	    bufferedReader.close();  
	   }  
	   if(fileReader!=null){  
	    fileReader.close();  
	   }  
	  }  
	    
	  return result;  
	 }

	 /**content 写入内容
	 * @param content
	 * @param fileName，绝对路径
	 * @return
	 */
	public boolean writeTxtFile(String content,File  fileName)throws Exception{
	  RandomAccessFile mm=null;  
	  boolean flag=false;  
	  FileOutputStream o=null;  
	  try {  
	   o = new FileOutputStream(fileName);  
	      o.write(content.getBytes("GBK"));  
	      o.close();   
	   flag=true;  
	  } catch (Exception e) {  
	   // TODO: handle exception  
	   e.printStackTrace();  
	  }finally{  
	   if(mm!=null){  
	    mm.close();  
	   }  
	  }  
	  return flag;  
	 }

	/**
	 * 判断文件是否存在
	 * @param filename
	 * @return
	 */
	public boolean haveTxtFile(File filename) {
		if (filename.exists()) {
			return true;
		}
		return false;
	}
}

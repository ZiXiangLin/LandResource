package com.gis.demo.tools;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

/**
 * 
 * @author Administrator
 *
 */
public class DownloadTifFile {
	/**
	 * @功能：从url下载tif文件并保存到本地
	 * @param url 请求的地址
	 * @param filePath 保存的文件地址
	 * @param method 请求的方法，包括POST和GET
	 * @return
	 */
	public static String SaveUrl(String url, String filePath, String method)
	{
		//创建不同的文件夹路径
		File file = new File(filePath);
		
		//判断文件夹是否存在
		if(!file.exists()){
			//如果文件不存在，则创建新的文件夹
			file.mkdirs();
		}
		
		FileOutputStream fileOutputStream = null;
		HttpURLConnection connection = null;
		InputStream inputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		BufferedInputStream bufferedInputStream = null;
		URL httpUrl = null;
		
		//最终返回的文件地址
		String fileName = null;
		try {
			//建立连接
			httpUrl = new URL(url);
			connection = (HttpURLConnection)httpUrl.openConnection();
			
			//以POST方式提交表单，默认get方式
			connection.setRequestMethod(method);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			
			//post方式不能使用缓存
			connection.setUseCaches(false);
			
			//连接指定的资源
			connection.connect();
			
			//获取网络输入流
			inputStream = connection.getInputStream();
		    bufferedInputStream = new BufferedInputStream(inputStream);
			
			//判断文件的保存路径的后面是否以"/"结尾
			if(!filePath.endsWith("/")){
				filePath += "/";
			}
			
			//组装最后的文件路径
			fileName = filePath + UUID.randomUUID().toString().replaceAll("-", "") + ".tif";
			
			//写入到文件
			fileOutputStream = new FileOutputStream(fileName);
			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			
			byte[] buf = new byte[4096];
			int length = bufferedInputStream.read(buf);
			
			//保存文件
			while(length != -1){
				bufferedOutputStream.write(buf, 0, length);
				length = bufferedInputStream.read(buf);
			}
			//关闭缓冲区
			bufferedOutputStream.close();
			bufferedOutputStream.close();
			connection.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileName;
	}
}

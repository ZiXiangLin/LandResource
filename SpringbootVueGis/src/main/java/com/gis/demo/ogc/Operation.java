package com.gis.demo.ogc;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Arcgis for server 10.1
 * 
 * @author HenC
 * @version 1.0
 */
public class Operation {


	private String _pyExePath;


	public Operation(String pyExePath) {
		this._pyExePath = pyExePath;
	}


	public boolean AddServicesFromSds(String pyFilePath, String host, int port,
			String username, String password, String serverFolder,
			String workspace, String sdDir) {
		Runtime rt = Runtime.getRuntime();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(host);
			sb.append(" ");
			sb.append(port);
			sb.append(" ");
			sb.append(username);
			sb.append(" ");
			sb.append(password);
			sb.append(" ");
			sb.append(serverFolder);
			sb.append(" ");
			sb.append(workspace);
			sb.append(" ");
			sb.append(sdDir);

			Process proc = rt.exec(_pyExePath + " " + pyFilePath + " "
					+ sb.toString());

			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			System.out.println("<ERROR>");
			while ((line = br.readLine()) != null)
				System.out.println(line);
			System.out.println("</ERROR>");
			int exitVal = proc.waitFor();
			System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	public boolean publishMXDtoservic(String publishMXDtoservicepy, String workspace,
			String mxdfilename, String con, String servicename) {
		Runtime rt = Runtime.getRuntime();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(workspace);
			sb.append(" ");
			sb.append(con);
			sb.append(" ");
			sb.append(mxdfilename);
			sb.append(" ");
			sb.append(servicename);

			Process proc = rt.exec(_pyExePath + " " + publishMXDtoservicepy + " "
					+ sb.toString());

			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			System.out.println("<ERROR>");
			while ((line = br.readLine()) != null)
				System.out.println(line);
			System.out.println("</ERROR>");
			int exitVal = proc.waitFor();
			System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;

	}

	public String CreateMXD(String CreateMXDpy, String workspace,String theShape)
	{
		Runtime rt = Runtime.getRuntime();
		String curTime=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String mxdpath = workspace + "\\" + curTime + "map.mxd"; 
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(theShape);
			sb.append(" ");
			sb.append(mxdpath);
			
			// ����������
			Process proc = rt.exec(_pyExePath + " " + CreateMXDpy + " "
					+ sb.toString());
			System.out.println(sb.toString());
			// �����ӡ��ErrorStream
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			System.out.println("<ERROR>");
			while ((line = br.readLine()) != null)
				System.out.println(line);
			System.out.println("</ERROR>");
			int exitVal = proc.waitFor();
			System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error: NO MXD FILE!";
		}
		
		return mxdpath;
	}

	public boolean EnableOGCServiceCmd(String pyFilePath, String host,
			String port, String username, String password,
			String serviceNameWithFolder, String serviceType, String ogcType) {
		Runtime rt = Runtime.getRuntime();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(username);
			sb.append(" ");
			sb.append(password);
			sb.append(" ");
			sb.append(host);
			sb.append(" ");
			sb.append(port);
			sb.append(" ");
			sb.append(serviceNameWithFolder);
			sb.append(" ");
			sb.append(serviceType);
			sb.append(" ");
			sb.append(ogcType);
			Process proc = rt.exec(_pyExePath + " " + pyFilePath + " "
					+ sb.toString());
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			System.out.println("<ERROR>");
			while ((line = br.readLine()) != null)
				System.out.println(line);
			System.out.println("</ERROR>");
			int exitVal = proc.waitFor();
			System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}


	public boolean EnableAllOGCServiceCmd(String pyFilePath, String host,
			int port, String username, String password,
			String serviceNameWithFolder, String serviceType) {
		Runtime rt = Runtime.getRuntime();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(username);
			sb.append(" ");
			sb.append(password);
			sb.append(" ");
			sb.append(host);
			sb.append(" ");
			sb.append(port);
			sb.append(" ");
			sb.append(serviceNameWithFolder);
			sb.append(" ");
			sb.append(serviceType);
			Process proc = rt.exec(_pyExePath + " " + pyFilePath + " "
					+ sb.toString());
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			System.out.println("<ERROR>");
			while ((line = br.readLine()) != null)
				System.out.println(line);
			System.out.println("</ERROR>");
			int exitVal = proc.waitFor();
			System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}


	public boolean AddGpBufferService(String pyFilePath, String username,
			String password, String host, String port, String workspace,
			String serviceName, String inputData, String envWorkspace,
			String distanceStr) {
		Runtime rt = Runtime.getRuntime();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(username);
			sb.append(" ");
			sb.append(password);
			sb.append(" ");
			sb.append(host);
			sb.append(" ");
			sb.append(port);
			sb.append(" ");
			sb.append(workspace);
			sb.append(" ");
			sb.append(serviceName);
			sb.append(" ");
			sb.append(inputData);
			sb.append(" ");
			sb.append(envWorkspace);
			sb.append(" ");
			sb.append(distanceStr);
			Process proc = rt.exec(_pyExePath + " " + pyFilePath + " "
					+ sb.toString());
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			System.out.println("<ERROR>");
			while ((line = br.readLine()) != null)
				System.out.println(line);
			System.out.println("</ERROR>");
			int exitVal = proc.waitFor();
			System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
	

	public boolean AddImageService(String pyFilePath,String servicename, String imgSource){
		Runtime rt = Runtime.getRuntime();
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(servicename);
			sb.append(" ");
			sb.append(imgSource);
			Process proc = rt.exec(_pyExePath + " " + pyFilePath + " "
					+ sb.toString());
			System.out.println( "java runtime exec:"+_pyExePath + " " + pyFilePath + " "
					+ sb.toString());

			//清空getErrorStream()缓冲区 解决waitFor方法阻塞无法返回的问题
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			System.out.println("<ERROR>");
			while ((line = br.readLine()) != null)
				System.out.println(line);
			System.out.println("</ERROR>");

			int exitVal = proc.waitFor();
			System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}


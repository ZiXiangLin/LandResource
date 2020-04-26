package com.gis.demo.tools;

import java.io.File;
import java.util.UUID;


public class CallID {
	public  String  wpsexecute2pCal (final String wpsurl,final String inputRaster) {
		final String s = UUID.randomUUID().toString().replaceAll("-", "");
		final FileOperation fOperation =new FileOperation();
		//使用线程来模拟同步调用
		new Thread() {
			String filePath = "C:\\CASAresult\\"+s+".txt";
			public void run() {
				WPSexecuter wpSexecuter = new WPSexecuter();
				String url=wpSexecuter.wpsexecute2p(wpsurl, inputRaster);
				File file = new File(filePath);
				try {
					fOperation.createFile(file);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					fOperation.writeTxtFile(url, file);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		}.start();
		return s;
		
	}
	public  String  wpsexecute3pCal (final String wpsurl,final String inputRaster,final String inputNumber) {
		final String s = UUID.randomUUID().toString().replaceAll("-", "");
		final FileOperation fOperation =new FileOperation();
		new Thread() {
			String filePath = "C:\\CASAresult\\"+s+".txt";
			
			public void run() {
				WPSexecuter wpSexecuter = new WPSexecuter();
				String url=wpSexecuter.wpsexecute3p(wpsurl, inputRaster, inputNumber);
				
				File file = new File(filePath);
				try {
					fOperation.createFile(file);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					fOperation.writeTxtFile(url, file);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		return s;
	}
	public  String  wpsexecute3pjjgCal (final String wpsurl,final String inputRaster1,final String inputRaster2) {
		final String s = UUID.randomUUID().toString().replaceAll("-", "");
		final FileOperation fOperation =new FileOperation();
		new Thread() {
			String filePath = "C:\\CASAresult\\"+s+".txt";
			
			public void run() {
				WPSexecuter wpSexecuter = new WPSexecuter();
				String url=wpSexecuter.wpsexecute3pjjg(wpsurl, inputRaster1, inputRaster2);
				
				File file = new File(filePath);
				try {
					fOperation.createFile(file);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					fOperation.writeTxtFile(url, file);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		return s;
	}
	public  String  wpsexecute4pjjgCal (final String wpsurl,final String inputRaster1,final String inputRaster2,final String inputRaster3) {
		final String s = UUID.randomUUID().toString().replaceAll("-", "");
		final FileOperation fOperation =new FileOperation();
		new Thread() {
			String filePath = "C:\\CASAresult\\"+s+".txt";
			
			public void run() {
				WPSexecuter wpSexecuter = new WPSexecuter();
				String url=wpSexecuter.wpsexecute4pjjg(wpsurl, inputRaster1, inputRaster2,inputRaster3);
				
				File file = new File(filePath);
				try {
					fOperation.createFile(file);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					fOperation.writeTxtFile(url, file);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		return s;
	}
	public  String readresult_txtfileCal(String id) throws Exception {
			File file = new File("C:\\CASAresult\\"+id+".txt");
			FileOperation fOperation = new FileOperation();
			if (fOperation.haveTxtFile(file)) {
				return fOperation.readTxtFile(file);
			}
			Thread.sleep(100);
			return("文件不存在");
	}
}


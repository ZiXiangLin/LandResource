package com.gis.demo.tools;

import org.dom4j.DocumentException;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;


public class WPSexecuter {
	
    public String wpsexecute2p(String wpsurl, String inputRaster)  {
		List<String> identifiers = null;
		try {
			identifiers = Identifier.getXMLneedIdentifiers(wpsurl);
			System.out.println("identifier::::"+identifiers);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String processidentifier= identifiers.get(0);
		String input1identifier=identifiers.get(1);
		String input2identifier=identifiers.get(2);
		String output= identifiers.get(3);
		System.out.println("identifier::::"+processidentifier+"+"+input1identifier+"+"+input2identifier);

		String version = "1.0.0";
		String requestXML = "<wps:DataInputs>"
	      		+ "<wps:Input>"
	      		+ "<ows:Identifier>"
	      		+ input1identifier
	      		+ "</ows:Identifier>"
	      		//+ "<ows:Title>InputDEM</ows:Title>"
	      		//+ "<wps:Reference xlink:href=\"https://192.168.1.107:6443/arcgis/services/ASTGTM2_N30E114_dem/ImageServer/WCSServer?request=GetCoverage&amp;service=wcs&amp;version=1.0.0&amp;COVERAGE=ASTGTM2_N30E114_dem&amp;crs=EPSG:4326&amp;format=GeoTIFF&amp;BBOX=114,30,114.5,30.5&amp;width=800&amp;height=800\"/>"
	      		+ "<wps:Reference xlink:href=\""
	      		+ inputRaster//rasterhref
	      		+ "\"/>"
	      		+ "</wps:Input>"
	      		+ "<wps:Input>"
        		+ "<ows:Identifier>"
	      		+ input2identifier
        		+ "</ows:Identifier>"
        		+ "<wps:Data>"
          		+ "<wps:LiteralData>\"urn:ogc:def:crs:EPSG::4326\"</wps:LiteralData>"
          		+ "</wps:Data>"
        		+ "</wps:Input>"
	      		+ "</wps:DataInputs>"
		      	+ "<wps:ResponseForm>"
		      	+ "<wps:ResponseDocument storeExecuteResponse=\"true\" lineage=\"true\" status=\"true\">"
		      	+ "<wps:Output asReference=\"true\">"
		      	+ "<ows:Identifier>"
		      	+ output
		      	+ "</ows:Identifier>"
		      	+ "</wps:Output>"
		      	+ "</wps:ResponseDocument>"
		      	+ "</wps:ResponseForm>";
		String res = "";

//		System.out.println("requestXML:::"+requestXML);

		try {
//			URL url = new URL(requestURL);
//			URLConnection con = url.openConnection();
			HttpsURLConnection.setDefaultHostnameVerifier(new WPSexecuter().new NullHostNameVerifier());
	        SSLContext sc = SSLContext.getInstance("TLS");
	        sc.init(null, trustAllCerts, new SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        URL url = new URL(wpsurl);
	        
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();
	        //con.setRequestMethod("POST");// POST GET PUT DELETE
			
			con.setDoOutput(true);
			//con.setRequestProperty("Pragma:", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", "text/xml");

			OutputStreamWriter out = new OutputStreamWriter(
					con.getOutputStream());

			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
			sb.append("<wps:Execute xmlns:wps=\"http://www.opengis.net/wps/");
			sb.append(version);
			sb.append("\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" service=\"WPS\" version=\"");
			sb.append(version);
			sb.append("\" xsi:schemaLocation=\"http://www.opengis.net/wps/");
			sb.append(version);
			sb.append(" http://schemas.opengis.net/wps/");
			sb.append(version);
			sb.append("/wpsExecute_request.xsd\">");
			sb.append("<ows:Identifier>");
			sb.append(processidentifier);
			sb.append("</ows:Identifier>");

			sb.append(requestXML);

			sb.append("</wps:Execute>");

			System.out.println("sb: "+sb);
			out.write(new String(sb.toString().getBytes("ISO-8859-1")));
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				res += line;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("res::::"+res);
		return res.substring(res.indexOf("' href='")+8, res.lastIndexOf("'"));
//		return res;
	}
 	public String wpsexecute2px(String wpsurl, String inputRaster)  {
		
		
		List<String> identifiers = null;
		try {
			identifiers = Identifier.getXMLneedIdentifiers(wpsurl);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String processidentifier= identifiers.get(0);
		String input1identifier=identifiers.get(1);
		String input2identifier=identifiers.get(2);
		String output= identifiers.get(3);
		String version = "1.0.0";
		String requestXML = "<wps:DataInputs>"
	      		+ "<wps:Input>"
	      		+ "<ows:Identifier>"
	      		+ input1identifier
	      		+ "</ows:Identifier>"
	      		//+ "<ows:Title>InputDEM</ows:Title>"
	      		//+ "<wps:Reference xlink:href=\"https://192.168.1.107:6443/arcgis/services/ASTGTM2_N30E114_dem/ImageServer/WCSServer?request=GetCoverage&amp;service=wcs&amp;version=1.0.0&amp;COVERAGE=ASTGTM2_N30E114_dem&amp;crs=EPSG:4326&amp;format=GeoTIFF&amp;BBOX=114,30,114.5,30.5&amp;width=800&amp;height=800\"/>"
	      		+ "<wps:Reference xlink:href=\""
	      		+ inputRaster//rasterhref
	      		+ "\"/>"
	      		+ "</wps:Input>"
	      		+ "<wps:Input>"
        		+ "<ows:Identifier>"
	      		+ input2identifier
        		+ "</ows:Identifier>"
        		+ "<wps:Data>"
          		+ "<wps:LiteralData>\"urn:ogc:def:crs:EPSG::4326\"</wps:LiteralData>"
          		+ "</wps:Data>"
        		+ "</wps:Input>"
	      		+ "</wps:DataInputs>"
		      	+ "<wps:ResponseForm>"
		      	+ "<wps:ResponseDocument storeExecuteResponse=\"true\" lineage=\"true\" status=\"true\">"
		      	+ "<wps:Output asReference=\"true\">"
		      	+ "<ows:Identifier>"
		      	+ output
		      	+ "</ows:Identifier>"
		      	+ "</wps:Output>"
		      	+ "</wps:ResponseDocument>"
		      	+ "</wps:ResponseForm>";
		String res = "";
		
		try {
//			URL url = new URL(requestURL);
//			URLConnection con = url.openConnection();
			HttpsURLConnection.setDefaultHostnameVerifier(new WPSexecuter().new NullHostNameVerifier());
	        SSLContext sc = SSLContext.getInstance("TLS");
	        sc.init(null, trustAllCerts, new SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        URL url = new URL(wpsurl);
	        
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();
	        //con.setRequestMethod("POST");// POST GET PUT DELETE
			
			con.setDoOutput(true);
			//con.setRequestProperty("Pragma:", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", "text/xml");

			OutputStreamWriter out = new OutputStreamWriter(
					con.getOutputStream());

			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
			sb.append("<wps:Execute xmlns:wps=\"http://www.opengis.net/wps/");
			sb.append(version);
			sb.append("\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" service=\"WPS\" version=\"");
			sb.append(version);
			sb.append("\" xsi:schemaLocation=\"http://www.opengis.net/wps/");
			sb.append(version);
			sb.append(" http://schemas.opengis.net/wps/");
			sb.append(version);
			sb.append("/wpsExecute_request.xsd\">");
			sb.append("<ows:Identifier>");
			sb.append(processidentifier);
			sb.append("</ows:Identifier>");

			sb.append(requestXML);

			sb.append("</wps:Execute>");

			System.out.println("sb: "+sb);

			out.write(new String(sb.toString().getBytes("ISO-8859-1")));
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				res += line;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
//				return res.substring(res.indexOf("' href='")+8, res.lastIndexOf("'"));
		System.out.println("res: " + res);
		return res;
	}
	public String wpsexecute3p(String requestURL, String inputRaster, String inputNumber) {
		
		ArrayList<String> identifiers = null;
		try {
			identifiers = Identifier.getXMLneedIdentifiers(requestURL);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String processidentifier= identifiers.get(0);
		String input1identifier=identifiers.get(1);
		String input2identifier=identifiers.get(2);
		String input3identifier=identifiers.get(3);
		String output= identifiers.get(4);
		String version = "1.0.0";
		String requestXML = "<wps:DataInputs>"
	      		+ "<wps:Input>"
	      		+ "<ows:Identifier>"
	      		+ input1identifier
	      		+ "</ows:Identifier>"
	      		//+ "<ows:Title>InputDEM</ows:Title>"
	      		//+ "<wps:Reference xlink:href=\"https://192.168.1.107:6443/arcgis/services/ASTGTM2_N30E114_dem/ImageServer/WCSServer?request=GetCoverage&amp;service=wcs&amp;version=1.0.0&amp;COVERAGE=ASTGTM2_N30E114_dem&amp;crs=EPSG:4326&amp;format=GeoTIFF&amp;BBOX=114,30,114.5,30.5&amp;width=800&amp;height=800\"/>"
	      		+ "<wps:Reference xlink:href=\""
	      		+ inputRaster//rasterhref
	      		+ "\"/>"
	      		+ "</wps:Input>"
	      		+ "<wps:Input>"
        		+ "<ows:Identifier>"
	      		+input2identifier
        		+ "</ows:Identifier>"
        		+ "<wps:Data>"
          		+ "<wps:LiteralData>"
        		+inputNumber
          		+ "</wps:LiteralData>"
          		+ "</wps:Data>"
        		+ "</wps:Input>"
	      		+ "<wps:Input>"
        		+ "<ows:Identifier>"
	      		+ input3identifier
        		+ "</ows:Identifier>"
        		+ "<wps:Data>"
          		+ "<wps:LiteralData>\"urn:ogc:def:crs:EPSG::4326\"</wps:LiteralData>"
          		+ "</wps:Data>"
        		+ "</wps:Input>"
	      		+ "</wps:DataInputs>"
		      	+ "<wps:ResponseForm>"
		      	+ "<wps:ResponseDocument storeExecuteResponse=\"true\" lineage=\"true\" status=\"true\">"
		      	+ "<wps:Output asReference=\"true\">"
		      	+ "<ows:Identifier>"
		      	+ output
		      	+ "</ows:Identifier>"
		      	+ "</wps:Output>"
		      	+ "</wps:ResponseDocument>"
		      	+ "</wps:ResponseForm>";
		String res = "";
		
		try {
//			URL url = new URL(requestURL);
//			URLConnection con = url.openConnection();
			HttpsURLConnection.setDefaultHostnameVerifier(new WPSexecuter().new NullHostNameVerifier());
	        SSLContext sc = SSLContext.getInstance("TLS");
	        sc.init(null, trustAllCerts, new SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        URL url = new URL(requestURL);
	        
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();
	        //con.setRequestMethod("POST");// POST GET PUT DELETE
			
			con.setDoOutput(true);
			//con.setRequestProperty("Pragma:", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", "text/xml");

			OutputStreamWriter out = new OutputStreamWriter(
					con.getOutputStream());

			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
			sb.append("<wps:Execute xmlns:wps=\"http://www.opengis.net/wps/");
			sb.append(version);
			sb.append("\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" service=\"WPS\" version=\"");
			sb.append(version);
			sb.append("\" xsi:schemaLocation=\"http://www.opengis.net/wps/");
			sb.append(version);
			sb.append(" http://schemas.opengis.net/wps/");
			sb.append(version);
			sb.append("/wpsExecute_request.xsd\">");
			sb.append("<ows:Identifier>");
			sb.append(processidentifier);
			sb.append("</ows:Identifier>");

			sb.append(requestXML);

			sb.append("</wps:Execute>");
			out.write(new String(sb.toString().getBytes("ISO-8859-1")));
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				res += line;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res.substring(res.indexOf("' href='")+8, res.lastIndexOf("'"));
//		return res;
	}
	public String wpsexecute3p2(String requestURL, String inputRaster, String inputRaster2) {
		
		ArrayList<String> identifiers = null;
		try {
			identifiers = Identifier.getXMLneedIdentifiers(requestURL);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String processidentifier= identifiers.get(0);
		String input1identifier=identifiers.get(1);
		String input2identifier=identifiers.get(2);
		String input3identifier=identifiers.get(3);
		String output= identifiers.get(4);
		String version = "1.0.0";
		String requestXML = "<wps:DataInputs>"
	      		+ "<wps:Input>"
	      		+ "<ows:Identifier>"
	      		+ input1identifier
	      		+ "</ows:Identifier>"
	      		//+ "<ows:Title>InputDEM</ows:Title>"
	      		//+ "<wps:Reference xlink:href=\"https://192.168.1.107:6443/arcgis/services/ASTGTM2_N30E114_dem/ImageServer/WCSServer?request=GetCoverage&amp;service=wcs&amp;version=1.0.0&amp;COVERAGE=ASTGTM2_N30E114_dem&amp;crs=EPSG:4326&amp;format=GeoTIFF&amp;BBOX=114,30,114.5,30.5&amp;width=800&amp;height=800\"/>"
	      		+ "<wps:Reference xlink:href=\""
	      		+ inputRaster//rasterhref
	      		+ "\"/>"
	      		+ "</wps:Input>"
	      		+ "<wps:Input>"
        		+ "<ows:Identifier>"
	      		+input2identifier
        		+ "</ows:Identifier>"
        		+ "<wps:Reference xlink:href=\""
	      		+ inputRaster2//rasterhref
	      		+ "\"/>"
        		+ "</wps:Input>"
	      		+ "<wps:Input>"
        		+ "<ows:Identifier>"
	      		+ input3identifier
        		+ "</ows:Identifier>"
        		+ "<wps:Data>"
          		+ "<wps:LiteralData>\"urn:ogc:def:crs:EPSG::4326\"</wps:LiteralData>"
          		+ "</wps:Data>"
        		+ "</wps:Input>"
	      		+ "</wps:DataInputs>"
		      	+ "<wps:ResponseForm>"
		      	+ "<wps:ResponseDocument storeExecuteResponse=\"true\" lineage=\"true\" status=\"true\">"
		      	+ "<wps:Output asReference=\"true\">"
		      	+ "<ows:Identifier>"
		      	+ output
		      	+ "</ows:Identifier>"
		      	+ "</wps:Output>"
		      	+ "</wps:ResponseDocument>"
		      	+ "</wps:ResponseForm>";
		String res = "";
		
		try {
//			URL url = new URL(requestURL);
//			URLConnection con = url.openConnection();
			HttpsURLConnection.setDefaultHostnameVerifier(new WPSexecuter().new NullHostNameVerifier());
	        SSLContext sc = SSLContext.getInstance("TLS");
	        sc.init(null, trustAllCerts, new SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        URL url = new URL(requestURL);
	        
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();
	        //con.setRequestMethod("POST");// POST GET PUT DELETE
			
			con.setDoOutput(true);
			//con.setRequestProperty("Pragma:", "no-cache");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", "text/xml");

			OutputStreamWriter out = new OutputStreamWriter(
					con.getOutputStream());

			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
			sb.append("<wps:Execute xmlns:wps=\"http://www.opengis.net/wps/");
			sb.append(version);
			sb.append("\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" service=\"WPS\" version=\"");
			sb.append(version);
			sb.append("\" xsi:schemaLocation=\"http://www.opengis.net/wps/");
			sb.append(version);
			sb.append(" http://schemas.opengis.net/wps/");
			sb.append(version);
			sb.append("/wpsExecute_request.xsd\">");
			sb.append("<ows:Identifier>");
			sb.append(processidentifier);
			sb.append("</ows:Identifier>");

			sb.append(requestXML);

			sb.append("</wps:Execute>");
			out.write(new String(sb.toString().getBytes("ISO-8859-1")));
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				res += line;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res.substring(res.indexOf("' href='")+8, res.lastIndexOf("'"));
//		return res;
	}
	public String wpsexecute3pjjg(String requestURL, String inputRaster1, String inputRaster2) {
		ArrayList<String> identifiers = null;
		try {
			identifiers = Identifier.getXMLneedIdentifiers(requestURL);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String processidentifier= identifiers.get(0);
		String input1identifier=identifiers.get(1);
		String input2identifier=identifiers.get(2);
		String input3identifier=identifiers.get(3);
		String output= identifiers.get(4);
		String version = "1.0.0";
		String requestXML = "<wps:DataInputs>"
				+ "<wps:Input>"
				+ "<ows:Identifier>"
				+ input1identifier
				+ "</ows:Identifier>"
				+ "<wps:Reference xlink:href=\""
				+ inputRaster1
				+ "\"/>"
				+ "</wps:Input>"
				+ "<wps:Input>"
				+ "<ows:Identifier>"
				+input2identifier
				+ "</ows:Identifier>"
				+ "<wps:Reference xlink:href=\""
				+ inputRaster2
				+ "\"/>"
				+ "</wps:Input>"
				+ "<wps:Input>"
				+ "<ows:Identifier>"
				+ input3identifier
				+ "</ows:Identifier>"
				+ "<wps:Data>"
				+ "<wps:LiteralData>\"urn:ogc:def:crs:EPSG::4326\"</wps:LiteralData>"
				+ "</wps:Data>"
				+ "</wps:Input>"
				+ "</wps:DataInputs>"
				+ "<wps:ResponseForm>"
				+ "<wps:ResponseDocument storeExecuteResponse=\"true\" lineage=\"true\" status=\"true\">"
				+ "<wps:Output asReference=\"true\">"
				+ "<ows:Identifier>"
				+ output
				+ "</ows:Identifier>"
				+ "</wps:Output>"
				+ "</wps:ResponseDocument>"
				+ "</wps:ResponseForm>";
		String res = "";
		try {
			HttpsURLConnection.setDefaultHostnameVerifier(new WPSexecuter().new NullHostNameVerifier());
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			URL url = new URL(requestURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", "text/xml");
			OutputStreamWriter out = new OutputStreamWriter(
					con.getOutputStream());
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
			sb.append("<wps:Execute xmlns:wps=\"http://www.opengis.net/wps/");
			sb.append(version);
			sb.append("\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" service=\"WPS\" version=\"");
			sb.append(version);
			sb.append("\" xsi:schemaLocation=\"http://www.opengis.net/wps/");
			sb.append(version);
			sb.append(" http://schemas.opengis.net/wps/");
			sb.append(version);
			sb.append("/wpsExecute_request.xsd\">");
			sb.append("<ows:Identifier>");
			sb.append(processidentifier);
			sb.append("</ows:Identifier>");
			sb.append(requestXML);
			sb.append("</wps:Execute>");
			out.write(new String(sb.toString().getBytes("ISO-8859-1")));
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				res += line;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res.substring(res.indexOf("' href='")+8, res.lastIndexOf("'"));
	}
	public String wpsexecute4pjjg(String requestURL, String inputRaster1, String inputRaster2,String inputRaster3) {
		ArrayList<String> identifiers = null;
		try {
			identifiers = Identifier.getXMLneedIdentifiers(requestURL);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String processidentifier= identifiers.get(0);
		String input1identifier=identifiers.get(1);
		String input2identifier=identifiers.get(2);
		String input3identifier=identifiers.get(3);
		String input4identifier=identifiers.get(4);
		String output= identifiers.get(5);
		String version = "1.0.0";
		String requestXML = "<wps:DataInputs>"
				+ "<wps:Input>"
				+ "<ows:Identifier>"
				+ input1identifier
				+ "</ows:Identifier>"
				+ "<wps:Reference xlink:href=\""
				+ inputRaster1
				+ "\"/>"
				+ "</wps:Input>"
				+ "<wps:Input>"
				+ "<ows:Identifier>"
				+input2identifier
				+ "</ows:Identifier>"
				+ "<wps:Reference xlink:href=\""
				+ inputRaster2
				+ "\"/>"
				+ "</wps:Input>"
				+ "<wps:Input>"
				+ "<ows:Identifier>"
				+input3identifier
				+ "</ows:Identifier>"
				+ "<wps:Reference xlink:href=\""
				+ inputRaster3
				+ "\"/>"
				+ "</wps:Input>"
				+ "<wps:Input>"
				+ "<ows:Identifier>"
				+ input4identifier
				+ "</ows:Identifier>"
				+ "<wps:Data>"
				+ "<wps:LiteralData>\"urn:ogc:def:crs:EPSG::4326\"</wps:LiteralData>"
				+ "</wps:Data>"
				+ "</wps:Input>"
				+ "</wps:DataInputs>"
				+ "<wps:ResponseForm>"
				+ "<wps:ResponseDocument storeExecuteResponse=\"true\" lineage=\"true\" status=\"true\">"
				+ "<wps:Output asReference=\"true\">"
				+ "<ows:Identifier>"
				+ output
				+ "</ows:Identifier>"
				+ "</wps:Output>"
				+ "</wps:ResponseDocument>"
				+ "</wps:ResponseForm>";
		String res = "";

		try {
			HttpsURLConnection.setDefaultHostnameVerifier(new WPSexecuter().new NullHostNameVerifier());
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			URL url = new URL(requestURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Content-Type", "text/xml");
			OutputStreamWriter out = new OutputStreamWriter(
					con.getOutputStream());
			StringBuilder sb = new StringBuilder();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>");
			sb.append("<wps:Execute xmlns:wps=\"http://www.opengis.net/wps/");
			sb.append(version);
			sb.append("\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:ows=\"http://www.opengis.net/ows/1.1\" service=\"WPS\" version=\"");
			sb.append(version);
			sb.append("\" xsi:schemaLocation=\"http://www.opengis.net/wps/");
			sb.append(version);
			sb.append(" http://schemas.opengis.net/wps/");
			sb.append(version);
			sb.append("/wpsExecute_request.xsd\">");
			sb.append("<ows:Identifier>");
			sb.append(processidentifier);
			sb.append("</ows:Identifier>");
			sb.append(requestXML);
			sb.append("</wps:Execute>");
			out.write(new String(sb.toString().getBytes("ISO-8859-1")));
			out.flush();
			out.close();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = "";
			for (line = br.readLine(); line != null; line = br.readLine()) {
				res += line;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res.substring(res.indexOf("' href='")+8, res.lastIndexOf("'"));
	}

	static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }
    } };

    public class NullHostNameVerifier implements HostnameVerifier {
        /*
         * (non-Javadoc)
         * 
         * @see javax.net.ssl.HostnameVerifier#verify(java.lang.String,
         * javax.net.ssl.SSLSession)
         */
        @Override
        public boolean verify(String arg0, SSLSession arg1) {
            // TODO Auto-generated method stub
            return true;
        }
    }

}

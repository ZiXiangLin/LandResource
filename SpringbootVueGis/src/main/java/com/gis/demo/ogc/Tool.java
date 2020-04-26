package com.gis.demo.ogc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.gis.demo.tools.StringUtils;



//暂时不用这个
public class Tool {
	
	public static String[] test(String value,String username,String filename){
		  String p = "<wps:DataInputs>"
		      		+ "<wps:Input>"
		      		+ "<ows:Identifier>inputDEM</ows:Identifier>"
				  //E:\RSplatform\\cby
		      		//+ "<wps:Reference xlink:href=\"http://localhost:6080/arcgis/services/dem/ImageServer/WCSServer?request=GetCoverage&amp;service=wcs&amp;version=1.0.0&amp;Coverage=dem&amp;crs=EPSG:4326&amp;format=GeoTIFF&amp;BBox=-105.46238150000001,39.882790499999999,-104.8276355,40.407580500000002&amp;width=761&amp;height=629\"/>"
		      		+ "<wps:Reference xlink:href=\""
		      		//+ "E:\\RSplatform\\DEM\\demflt.tif"//C:\\Users\\Tdream\\Desktop
		      		+ "E:\\RSplatform\\userdata\\"+username+"\\"+filename
		      		
		      		//+ fileabsoluUserstepath 
		      		+ "\"/>"
		      		+ "</wps:Input>"
		      		+ "<wps:Input>"
		      		+ "<ows:Identifier>flowvalue</ows:Identifier>"
		      		+ "<wps:Data>"
		      		+ "<wps:LiteralData>"
		      		+ value
		      		//+ "300"
		      		+ "</wps:LiteralData>"
		      		+ "</wps:Data>"
		      		+ "</wps:Input>"
		      		+ "</wps:DataInputs>";
	       //System.out.println(fileabsolutepath);
	       //System.out.println(flowvalue);
	       //System.out.println("E:\\publishexe\\imagefile\\dem.flt");
	       String reString= GetWPSResult("https://localhost:6443/arcgis/services/models/createrivernet/GPServer/WPSServer", "1.0.0", "createrivernet", p);
	       System.out.println(reString);
	       System.out.println(StringUtils.findId(reString));
	       System.out.println(StringUtils.findPartPath(reString));
	       String processsid = StringUtils.findId(reString);
	       String wps_dir = StringUtils.findPartPath(reString).replace('/', '\\').trim() + "_gpserver\\";
	       String serverjob_dir = "D:\\arcgisserver\\directories\\arcgisjobs\\";
	       
	       String scratch = serverjob_dir + wps_dir + processsid.trim() + "\\" + "scratch";
	       String resultfile = serverjob_dir + wps_dir + processsid.trim() + "\\" + "scratch\\scratch.gdb\\" + "RasterT333";
	       System.out.println(scratch);
	       System.out.println(resultfile);
	       String[] result = {scratch,resultfile};
	       System.out.println(result[0]);
	       System.out.println(result[1]);
	       return result;
	       
	}
	
	
	public static String GetWPSResult(String requestURL, String version,
			String identifier, String paramXML) {
		String res = "";
		if (version == "" || version == null)
			version = "1.0.0";
		try {
//			URL url = new URL(requestURL);
//			URLConnection con = url.openConnection();
			HttpsURLConnection.setDefaultHostnameVerifier(new Tool().new NullHostNameVerifier());
	        SSLContext sc = SSLContext.getInstance("TLS");
	        sc.init(null, trustAllCerts, new SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        URL url = new URL(requestURL);
	        // ��restful����
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
			sb.append(identifier);
			sb.append("</ows:Identifier>");

			sb.append(paramXML);

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
		return res;
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
	public static String wfs(String theShape)
	{
		String pyExePath = "C:\\Python27\\ArcGISx6410.5\\python.exe";
		Operation opt = new Operation(pyExePath);
		String createMXDpy = "E:\\RSplatform\\wjc\\Py\\createMXD.py";
		String publishMXDtoservicepy = "E:\\RSplatform\\wjc\\Py\\publishMXDtoservice.py";
		String workspace = "E:\\tmp";
		String curTime=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String servicename =  "geomap" + curTime;//arcgisserver�е�ͼ���������
		//���ļ����������Ҫ�����ļ���shp�ļ���ags�ļ�
		//theShape = "D:\\arcgisserver\\directories\\arcgisjobs\\models\\createrivernet_gpserver\\jbff23e147c554d6dbda34717e64e33ce\\scratch\\scratch.gdb\\RasterT333";//Ҫ��ȡ�����Ľ������shp��img��ʽ
		String con = "E:\\tmp\\myags.ags";//�ֶ�������ags�ļ�
		String mxdpath = opt.CreateMXD(createMXDpy, workspace, theShape );
		System.out.println(mxdpath);
		boolean res = opt.publishMXDtoservic(publishMXDtoservicepy, workspace, mxdpath, con, servicename);
		if(res){
			System.out.println("Result: AddMapServiceSuccess");
		}else{
			System.out.println("Result: AddMapServiceFailed");
		}
		System.out.println("-------------------------------");
		String url =  "https://localhost:6443/arcgis/rest/services/WP_Map/" + servicename + "/MapServer";//��ͼ�����url
		return url;
	}
	
	/**
	 * �����ѷ��������OGC��׼�ķ�����
	 * 
	 * @param pyFilePath
	 *            Python�ļ���λ��(..../....py)
	 * @param host
	 *            ������
	 * @param port
	 *            ����˿�
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 * @param serviceNameWithFolder
	 *            ������Ҫ�޸Ĳ����ķ�������ע��Ҫ�����ļ�·��
	 * @param serviceType
	 *            �������� ���� MapServer��
	 * @param ogcType
	 *            Ҫ������OGC��׼��������
	 * @return ��ʾ�����Ƿ�ɹ���booleanֵ
	 */
	public static boolean EnableOGCServiceCmd(String serviceNameWithFolder, String serviceType, String ogcType) {
		String pyFilePath = "E:\\RSplatform\\wjc\\Py\\EnableService.py";
		String host = "localhost";
		String port = "6080";
		String username = "xj199608";
		String password = "x19960817j";
   /*	String serviceNameWithFolder = "/WP_Map/GeoRiver";
		String serviceType = "MapServer";
		String ogcType = "WFS";*/
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
			String pyExePath = "C:\\Python27\\ArcGISx6410.5\\python.exe";
			Process proc = rt.exec(pyExePath + " " + pyFilePath + " "
					+ sb.toString());
			String s = sb.toString();
			System.out.println(s);
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

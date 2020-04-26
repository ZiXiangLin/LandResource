package com.gis.demo.tools;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class WPS {

	public static String execute(String wpsURL, String version,
			String identifier, String requestXML) {
		String res = "";
		if (version == "" || version == null)
			version = "1.0.0";
		try {
//			URL url = new URL(requestURL);
//			URLConnection con = url.openConnection();
			HttpsURLConnection.setDefaultHostnameVerifier(new WPS().new NullHostNameVerifier());
	        SSLContext sc = SSLContext.getInstance("TLS");
	        sc.init(null, trustAllCerts, new SecureRandom());
	        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
	        URL url = new URL(wpsURL);
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
}

package com.gis.demo.ogc;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class HttpRequest {
    public static String sendGetMsg(String requestURL, String param) {
        requestURL = requestURL+"?"+param;
        String result = "";
        try {
            URL url = new URL(requestURL);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.connect();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {
                result += line;
            }
        } catch (IOException ex) {
            return ex.getMessage();
        }
        return result;
    }
    public static String sendPostMsg(String requestURL, String requestXML) {
        String result = "";
        try {
            URL url = new URL(requestURL);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");
            OutputStreamWriter out = new OutputStreamWriter(
                    con.getOutputStream());
            out.write(new String(requestXML.getBytes("ISO-8859-1")));
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String line = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {
                result += line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String getWCSgetcoverage(String requestURL, String param) throws IOException {
        requestURL = requestURL+"?"+param;
        URL url = new URL(requestURL);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
        conn.connect();
        try {
            byte[] data= readInputStream(conn.getInputStream());
            File imageFile = new File("C:/Users/LinZiXiang/Desktop/AT/AT/result/result_get.tiff");
            System.out.println("��ʼд��");
            FileOutputStream outStream = new FileOutputStream(imageFile);
            outStream.write(data);
            outStream.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "��ȡ�ɹ�";
    }

    public static String postWCSgetcoverage(String requestURL, String requestXML) throws Exception {
        URL url = new URL(requestURL);
        URLConnection con = url.openConnection();
        con.setDoOutput(true);
        con.setRequestProperty("Pragma", "no-cache");
        con.setRequestProperty("Cache-Control", "no-cache");
        con.setRequestProperty("Content-Type", "text/xml");
        //����������Ϣ��
        OutputStreamWriter out = new OutputStreamWriter(
                con.getOutputStream());
        out.write(new String(requestXML.getBytes("ISO-8859-1")));
        out.flush();
        out.close();
        //�������󣬽��շ�����Ϣ
        byte[] data= readInputStream(con.getInputStream());
        File imageFile = new File("C:/Users/LinZiXiang/Desktop/AT/AT/result/result_post.tiff");
        System.out.println("��ʼд��");
        FileOutputStream outStream = new FileOutputStream(imageFile);
        outStream.write(data);
        outStream.close();
        return "��ȡ�ɹ�";
    }

    public static byte[] readInputStream(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    public static void main(String[] args) {



    }
}

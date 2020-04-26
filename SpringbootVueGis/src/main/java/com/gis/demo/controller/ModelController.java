package com.gis.demo.controller;

import com.gis.demo.domain.ModelParament.StreamExtraction;
import com.gis.demo.domain.Service;
import com.gis.demo.domain.ModelParament.RainDisaster;
import com.gis.demo.domain.ModelParament.CASANPP;
import com.gis.demo.domain.Uav;
import com.gis.demo.domain.Wcs;
import com.gis.demo.service.ServiceService;

import com.gis.demo.service.UavService;
import com.gis.demo.service.WcsService;
import com.gis.demo.tools.WCSprovider;
import com.gis.demo.tools.WPSexecuter;
import com.gis.demo.tools.CallID;
import com.gis.demo.tools.Fill;
import com.gis.demo.tools.FlowAccumulation;
import com.gis.demo.tools.FlowDirection;
import com.gis.demo.tools.GreaterThan;
import com.gis.demo.tools.RasterToPolyline;
import com.gis.demo.tools.ParseXML;
import com.gis.demo.tools.DownloadTifFile;

import com.gis.demo.ogc.PublishWCS;
import com.gis.demo.ogc.HttpRequest;

import com.gis.demo.tools.jps.GPSgeter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

import java.net.MalformedURLException;
import java.sql.SQLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class ModelController {

    @Autowired
    private ServiceService serviceService;
    @Autowired
    private WcsService wcsService;
    @Autowired
    private UavService uavService;

    // platform\MySystem\src\com\tdream\web\FindServiceServlet.java
    // 中getUrlList(data/tool)功能
    // 1.查询service 中type 为data的URL
    @CrossOrigin
    @PostMapping(value = "api/getDataUrl")
    @ResponseBody
    public List<Service> getDataUrl(HttpSession session) {
        String type = "data";
        List<Service> serviceList = serviceService.findByType(type);
        return serviceList;
    }
    // 2.查询service 中type 为data的URL
    @CrossOrigin
    @PostMapping(value = "api/getToolUrl")
    @ResponseBody
    public List<Service> getToolUrl(HttpSession session) {
        String type = "tool";
        List<Service> serviceList = serviceService.findByType(type);
        return serviceList;
    }
    // platform\MySystem\src\com\tdream\web\FindServiceServlet.java
    // 中getOneUrl功能
    @CrossOrigin
    @PostMapping(value = "api/getOneUrl")
    @ResponseBody
    public String getOneUrl(HttpServletRequest request,HttpSession session){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = br.readLine())!=null){
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String data = sb.toString();
        String cnname="";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(data));
            cnname = (String)jsonObject.get("CNNAME");
        }catch (ParseException e){
            e.printStackTrace();
        }
        System.out.println(cnname);
        Service service = serviceService.findByCnname(cnname);
        return service.getURL();
    }



    @CrossOrigin
    @PostMapping(value = "api/Test")
    @ResponseBody
    public String ForTest(HttpServletRequest request,HttpSession session){
        System.out.println("-------------- For Test ==============> ");
        // STEP-0 TEST THIS INTERFACE WORK OR NOT -2020--04-11 LINZIXIANG
        // STEP1:WCS服务 getCapabilities -> describeCoverage -> getCoverage
        String wcsurl = "http://localhost:6080/arcgis/services/WCS/MODEV1D.20160517.CN.EVI.V2.TIF20200311232622603/ImageServer/WCSServer";
        String tif = null;
        if(wcsurl.contains("geoserver")){
            System.out.println("====> wcs url with geoserver");
            tif = wcsurl;
        }else{
            System.out.println("====> wcs url with arcgis server");
            WCSprovider wcs= new WCSprovider();
            tif=wcs.getCoverage(wcsurl);
        }
        System.out.println("通过 wcs.getCoverage 得到的数据地址::::  "+tif);
        //STEP2: WPS executer
        WPSexecuter wps = new WPSexecuter();
        String slope ="http://localhost:6080/arcgis/services/MySystem/Slope/GPServer/WPSServer";
        String slopetif= wps.wpsexecute2px(slope,tif);
        System.out.println("wps execute"+slopetif);

        return "test";
        //STEP3: DownloadTif
        //String contif="http://localhost:6080/arcgis/services/MySystem/PM25Tif/MapServer/WCSServer?request=GetCoverage&amp;service=wcs&amp;version=1.0.0&amp;COVERAGE=1&amp;crs=EPSG:4326&amp;format=GeoTIFF&amp;BBOX=115.4148,39.442979999999999,117.4988,41.058979999999998&amp;width=1041&amp;height=807";
        //String contif="";
        // ===========START 保存最终结果到本地====================
        //String filePath = DownloadTifFile.SaveUrl(contif, "C:\\Users\\LinZiXiang\\Desktop\\SpringbootVueGis\\output\\", "GET");
        //System.out.println("FILE PATH:"+filePath);
        //========START======将结果发布为WCS服务====================
        //final String service_name = UUID.randomUUID().toString().replaceAll("-", "");

        //调用WCS服务发布函数，进行服务发布
        //String wcsURL = PublishWCS.wcs(filePath, service_name);

        //return "test";
    }

    @CrossOrigin
    @PostMapping(value = "api/Test2")
    @ResponseBody
    public String ForTest2(HttpServletRequest request,HttpSession session){
        System.out.println("-------------- For Test2 ==============> ");
        String param2="SERVICE=WCS&VERSION=1.0.0&REQUEST=GetCoverage&Coverage=smallsub&crs=EPSG:4326&format=GeoTIFF&BBox=114.13813710173059,30.42357411418606,114.52621490077652,30.726972419428588&width=1273&height=1143";
        String s2="��ȡʧ��";
        try {
            s2 = HttpRequest.getWCSgetcoverage("http://localhost:6080/arcgis/services/MySystem/smallsub/ImageServer/WCSServer",param2);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(s2);

        //���� POST ����
        String requestXML2="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                + "<GetCoverage version=\"1.0.0\" "
                + "service=\"WCS\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xmlns=\"http://www.opengis.net/wcs\" "
                + "xmlns:ows=\"http://www.opengis.net/ows/1.1\" "
                + "xmlns:gml=\"http://www.opengis.net/gml\" "
                + "xmlns:ogc=\"http://www.opengis.net/ogc\" "
                + "xsi:schemaLocation=\"http://www.opengis.net/wcs http://schemas.opengis.net/wcs/1.0.0/getCoverage.xsd\">"
                + "<sourceCoverage>smallsub</sourceCoverage>"
                + "<domainSubset>"
                + "<spatialSubset>"
                + "<gml:Envelope srsName=\"EPSG:4326\">"
                + "<gml:pos>114.13813710173059 30.42357411418606</gml:pos>"
                + "<gml:pos>114.52621490077652 30.726972419428588</gml:pos>"
                + "</gml:Envelope>"
                + "<gml:Grid dimension=\"2\">"
                + "<gml:limits>"
                + "<gml:GridEnvelope>"
                + "<gml:low>0 0</gml:low>"
                + "<gml:high>1273 1143</gml:high>"
                + "</gml:GridEnvelope>"
                + "</gml:limits>"
                + "<gml:axisName>x</gml:axisName>"
                + "<gml:axisName>y</gml:axisName>"
                + "</gml:Grid>"
                + "</spatialSubset>"
                + "</domainSubset>"
                + "<output>"
                + "<crs>EPSG:4326</crs>"
                + "<format>GeoTIFF</format>"
                + "</output>"
                + "</GetCoverage>";
        String sr2="��ȡʧ��";
        try {
            sr2 = HttpRequest.postWCSgetcoverage("http://localhost:6080/arcgis/services/MySystem/smallsub/ImageServer/WCSServer", requestXML2);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(sr2);

        return "test";

    }

    @CrossOrigin
    @PostMapping(value = "api/Test3")
    @ResponseBody
    public String ForTest3(HttpServletRequest request,HttpSession session){
        System.out.println("-------------- For Test3qq ==============> ");
        String requestXML2="<wps:Execute xmlns:wps=\"http://www.opengis.net/wps/1.0.0\" "
                + "xmlns:xlink=\"http://www.w3.org/1999/xlink\" "
                + "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
                + "xmlns:ows=\"http://www.opengis.net/ows/1.1\" "
                + "service=\"WPS\" "
                + "version=\"1.0.0\" "
                + "xsi:schemaLocation=\"http://www.opengis.net/wps/1.0.0 http://schemas.opengis.net/wps/1.0.0/wpsExecute_request.xsd\">"
                + "<ows:Identifier>Buffer</ows:Identifier>"
                + "<wps:DataInputs>"
                + "<wps:Input>"
                + "<ows:Identifier>Distance</ows:Identifier>"
                + "<wps:Data>"
                + "<wps:LiteralData>400</wps:LiteralData>"
                + "</wps:Data>"
                + "</wps:Input>"
                + "<wps:Input>"
                + "<ows:Identifier>InputFeatures</ows:Identifier>"
                + "<wps:Reference xlink:href=\"http://localhost:6080/arcgis/services/data/MapServer/WFSServer?service=wfs&amp;request=GetFeature&amp;version=1.0.0&amp;typename=inputpoints\"/>"
                + "</wps:Input>"
                + "</wps:DataInputs>"
                + "<wps:ResponseForm>"
                + "<wps:ResponseDocument storeExecuteResponse=\"true\" lineage=\"true\" status=\"true\">"
                + "<wps:Output asReference=\"true\">"
                + "<ows:Identifier>OutputFeatureClass</ows:Identifier>"
                + "<ows:Title>OutputFeatureClass</ows:Title>"
                + "</wps:Output>"
                + "</wps:ResponseDocument>"
                + "</wps:ResponseForm>"
                + "</wps:Execute>";
        String sr2=HttpRequest.sendPostMsg("http://localhost:6080/arcgis/services/WPS/Buffer/GPServer/WPSServer", requestXML2);
        System.out.println(sr2);
        return "test";

    }

    // 执行Model: RainDisaster
    // platform\MySystem\src\com\tdream\models\RainDisasterServlet.java
    @CrossOrigin
    @PostMapping(value = "api/RainDisaster")
    @ResponseBody
    public String RainDisaster(@RequestBody RainDisaster requestDisaster, HttpSession session) {
        System.out.println("------------------------------------- RainDisaster ------------------------------------");
        //DEM数据
        String DEMurl = requestDisaster.getInputDem();
        //DEM精度值，如0.05m
        String dem_accuracy = requestDisaster.getThreshold() + "";
        //WPS1:去除DEM数据错误，理论上小于DEM精度的地方无错误点
        String error = requestDisaster.getDeleteError();
        //WPS2:填洼地操作，洼地会被填充
        String fillpits = requestDisaster.getFill();
        //wps3：栅格相减，无洼地DEM减去去除了错误的DEM，即为洼地的深度
        String minus = requestDisaster.getMinus();
        //WPS4：栅格条件函数，二值化，栅格深度大于0的位置即为洼地所在位置，大于0的赋值为1，等于0的还是0
        String condition = requestDisaster.getCondition();
        System.out.println("DEMurl: "+DEMurl);
        System.out.println("Dem_accuracy: "+dem_accuracy);
        System.out.println("error: "+error);
        System.out.println("fillpits: "+fillpits);
        System.out.println("minus: "+minus);
        System.out.println("condition: "+condition);
        String tif = null;

        //判断输入的WCS服务是由geoserver发布的还是有arcgis server发布的
        if(DEMurl.contains("geoserver")){
            //若输入的WCS url是geoserver发布的
            tif = DEMurl;
        }else{
            //若输入的WCS url是arcgis server发布的
            //TIF: WCSurl+"?request=GetCoverage&amp;service=wcs&amp;version=1.0.0&amp;COVERAGE=1"+"&amp;crs="+crs+"&amp;format=GeoTIFF&amp;BBOX="+BBox+"&amp;width="+width+"&amp;height="+height
            WCSprovider wcs= new WCSprovider();
            tif=wcs.getCoverage(DEMurl);
        }


        /*
         * http://139.198.6.203:8080/geoserver/wcs?request=GetCoverage&amp;service=wcs&amp;version=1.0.0&amp;sourceCoverage=whu.images:DEM222_ProjectRaster1&amp;crs=EPSG:4326&amp;format=GeoTIFF&amp;BBox=12.47951808054628, 55.71187969366227,12.622415925759986,55.7914637901954&amp;width=7114&amp;height=3962
         *
         */

        System.out.println("数据地址："+tif);
        WPSexecuter wps = new WPSexecuter();
        String errortif= wps.wpsexecute3p(error, tif, dem_accuracy);
        //http://139.198.6.203:6080/arcgis/services/WPSServices/DeleteError/GPServer/WPSServer
        System.out.println(errortif);

        String filltif= wps.wpsexecute2p(fillpits,tif);
        System.out.println(filltif);

        String minutif= wps.wpsexecute3p2(minus, filltif,errortif);
        System.out.println(minutif);

        String contif= wps.wpsexecute2p(condition, minutif);
        System.out.println(contif);

        //String url=RasterToPolyline.run(GreaterThan.run(FlowAccumulation.run(FlowDirection.run(Fill.run(DEMurl,fillurl),flowdirectionurl),flowaccumulationurl), threshold, flowthresholdurl),rester_to_vectorurl);

        //===========START 保存最终结果到本地====================
        String filePath = DownloadTifFile.SaveUrl(contif, "C:\\Users\\LinZiXiang\\Desktop\\SpringbootVueGis\\output\\", "GET");
        //===========END 保存最终结果到本地======================
        //========START======将结果发布为WCS服务====================
        //随机生成WCS服务名称
        final String service_name = UUID.randomUUID().toString().replaceAll("-", "");

		//调用WCS服务发布函数，进行服务发布
        String wcsURL = PublishWCS.wcs(filePath, service_name);
        //========END======将结果发布为WCS服务======================

        String disaster = "http://localhost:6080/arcgis/rest/services/WCS/" + wcsURL +"/ImageServer";
        //String disaster = "http://localhost:6080/arcgis/rest/services/WCS/MODEV1D.20160517.CN.EVI.V2.TIF20200311232622603/ImageServer";
        return disaster;
    }

    // 执行Model: CASANPP
    // platform\MySystem\src\com\tdream\models\CASANPPModelServlet.java
    @CrossOrigin
    @PostMapping(value = "api/CASANPP")
    @ResponseBody
    public String CASANPP(@RequestBody CASANPP requestCASANPP, HttpSession session) {
        //DEM数据
        String NDVIurl = requestCASANPP.getNdvi();
        String SOLurl = requestCASANPP.getSol();
        String Turl = requestCASANPP.getT();
        String T_OPT = requestCASANPP.gettOpt();
        String ETurl = requestCASANPP.getEt();
        String PETurl = requestCASANPP.getPet();
        String CalculateSRurl = requestCASANPP.getSr();
        String CalculateFPARurl = requestCASANPP.getFpar();
        String CalculateAPARurl = requestCASANPP.getApar();
        String CalT1url = requestCASANPP.getT1();
        String CalculateT2url = requestCASANPP.getT2();
        String CalculateWurl = requestCASANPP.getW();
        String CalculateEurl = requestCASANPP.getE();
        String CalculateNPPurl = requestCASANPP.getNpp();
        System.out.println(NDVIurl);
        System.out.println(SOLurl);
        System.out.println(Turl);
        System.out.println(T_OPT);
        System.out.println(ETurl);
        System.out.println(PETurl);
        System.out.println(CalculateSRurl);
        System.out.println(CalculateFPARurl);
        System.out.println(CalculateAPARurl);
        System.out.println(CalculateT2url);
        System.out.println(CalculateWurl);
        System.out.println(CalculateEurl);
        System.out.println(CalculateNPPurl);
        CallID callID = new CallID();
        String Url="";
        try{
            System.out.println("执行WP服务CalculateSR开始ʼ");
            String id1 = callID.wpsexecute2pCal(CalculateSRurl,NDVIurl);
            System.out.println(id1);
            String resulturl_SR = callID.readresult_txtfileCal(id1);
            while(resulturl_SR == "文件不存在")
            {
                resulturl_SR = callID.readresult_txtfileCal(id1);
            }
            System.out.println(resulturl_SR);
            System.out.println("执行WP服务CalculateSR结束");
            System.out.println("执行WP服务CalculateFPAR开始");
            String id2 = callID.wpsexecute2pCal(CalculateFPARurl, resulturl_SR);
            System.out.println(id2);
            String resulturl_FPAR = callID.readresult_txtfileCal(id2);
            while(resulturl_FPAR == "文件不存在")
            {
                resulturl_FPAR = callID.readresult_txtfileCal(id2);
            }
            System.out.println(resulturl_FPAR);
            System.out.println("执行WP服务CalculateFPAR结束");
            System.out.println("执行WP服务CalculateAPAR开始");
            String id3 = callID.wpsexecute3pjjgCal(CalculateAPARurl,SOLurl,resulturl_FPAR);
            System.out.println(id3);
            String resulturl_APAR = callID.readresult_txtfileCal(id3);
            while(resulturl_APAR == "文件不存在")
            {
                resulturl_APAR = callID.readresult_txtfileCal(id3);
            }
            System.out.println(resulturl_APAR);
            System.out.println("ִ执行WP服务CalculateAPAR结束");
            System.out.println("执行WP服务CalculateT1开始");
            String id4 = callID.wpsexecute2pCal(CalT1url, Turl);
            System.out.println(id4);
            String resulturl_T1 = callID.readresult_txtfileCal(id4);
            while(resulturl_T1 == "文件不存在")
            {
                resulturl_T1 = callID.readresult_txtfileCal(id4);
            }
            System.out.println(resulturl_T1);
            System.out.println("执行WP服务CalculateT1结束");
            System.out.println("执行WP服务CalculateT2开始");
            String id5 = callID.wpsexecute3pCal(CalculateT2url,Turl,T_OPT);
            System.out.println(id5);
            String resulturl_T2 = callID.readresult_txtfileCal(id5);
            while(resulturl_T2 == "文件不存在")
            {
                resulturl_T2 = callID.readresult_txtfileCal(id5);
            }
            System.out.println(resulturl_T2);
            System.out.println("执行WP服务CalculateT2结束");
            System.out.println("执行WP服务CalculateW开始");
            String id6 = callID.wpsexecute3pjjgCal(CalculateWurl, ETurl,PETurl);
            System.out.println(id6);
            String resulturl_W = callID.readresult_txtfileCal(id6);
            while(resulturl_W == "文件不存在")
            {
                resulturl_W = callID.readresult_txtfileCal(id6);
            }
            System.out.println(resulturl_W);
            System.out.println("执行WP服务CalculateW结束");
            System.out.println("执行WP服务CalculateE开始");
            String id7 = callID.wpsexecute4pjjgCal(CalculateEurl, resulturl_T1,resulturl_T2,resulturl_W);
            System.out.println(id7);
            String resulturl_E = callID.readresult_txtfileCal(id7);
            while(resulturl_E == "文件不存在")
            {
                resulturl_E = callID.readresult_txtfileCal(id7);
            }
            System.out.println(resulturl_E);
            System.out.println("ִ执行WP服务CalculateE结束");
            System.out.println("执行WP服务CalculateNPP开始");
            String id8 = callID.wpsexecute3pjjgCal(CalculateNPPurl, resulturl_APAR, resulturl_E);
            System.out.println(id8);
            String resulturl_NPP = callID.readresult_txtfileCal(id8);
            while(resulturl_NPP == "文件不存在")
            {
                resulturl_NPP = callID.readresult_txtfileCal(id8);
            }
            System.out.println(resulturl_NPP);
            System.out.println("执行WP服务CalculateNPP结束");
            //将NPP的tif结果文件保存到本地，然后发布WCS服务，之后将WCS服务数据显示到平台中
            String filePath = DownloadTifFile.SaveUrl(resulturl_NPP, "I:\\platform\\Project\\MySystem\\output\\", "GET");
            final String service_name = UUID.randomUUID().toString().replaceAll("-", "");
            String wcsURL = PublishWCS.wcs(filePath, service_name);
            Url = "http://localhost:6080/arcgis/rest/services/WCS/" + wcsURL +"/ImageServer";
            return Url;

        }catch(Exception e){
            e.printStackTrace();
        }
        return Url;
    }


    // 执行Model: StreamExtraction
    // platform\MySystem\src\com\tdream\models\StreamExtractionServlet.java
    @CrossOrigin
    @PostMapping(value = "api/StreamExtraction")
    @ResponseBody
    public String StreamExtraction(@RequestBody StreamExtraction requestStreamExtraction,HttpSession session){
        String DEMurl = requestStreamExtraction.getDEMurl();
        String threshold = requestStreamExtraction.getThreshold();
        System.out.println("threshold:"+threshold);
        //wps url
        String fillurl = requestStreamExtraction.getFillurl();
        String flowdirectionurl = requestStreamExtraction.getFlowdirectionurl();
        String flowaccumulationurl = requestStreamExtraction.getFlowaccumulationurl();
        String flowthresholdurl = requestStreamExtraction.getFlowthresholdurl();
        String rester_to_vectorurl = requestStreamExtraction.getRester_to_vectorurl();

        WCSprovider wcs= new WCSprovider();
        String tif=wcs.getCoverage(DEMurl);
        WPSexecuter wps = new WPSexecuter();
        String fill= wps.wpsexecute2p(fillurl,tif );
        String dir = wps.wpsexecute2p(flowdirectionurl, fill);
        String acc= wps.wpsexecute2p(flowaccumulationurl, dir);
        String gt= wps.wpsexecute3p(flowthresholdurl, acc, threshold);
        String lineurl=wps.wpsexecute2p(rester_to_vectorurl, gt);

        String url=RasterToPolyline.run(GreaterThan.run(FlowAccumulation.run(FlowDirection.run(Fill.run(DEMurl,fillurl),flowdirectionurl),flowaccumulationurl), threshold, flowthresholdurl),rester_to_vectorurl);
        return url;
    }

    // platform\MySystem\src\com\tdream\models\ParseXMLByURLServlet.java
    @CrossOrigin
    @PostMapping(value = "api/ParseXMLByURL")
    @ResponseBody
    public String ParseXMLByURL(HttpServletRequest request,HttpSession session){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line = null;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = br.readLine())!=null){
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String data = sb.toString();
        String url="";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(data));
            url = (String)jsonObject.get("url");
        }catch (ParseException e){
            e.printStackTrace();
        }
        String result="";
        try {
            System.out.println("parseXML:" + url);
            ParseXML parseXML=new ParseXML(new URL(url));
//			ParseXML parseXML=new ParseXML(new File("http://139.198.6.203:6080/arcgis/services/WCSServices/AreaDEM/ImageServer/WCSServer"));
            result=parseXML.toJsonStringR();
            System.out.println("result:"+result);

        } catch (DocumentException | MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


    private static final long serialVersionUID = 1L;
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 30MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 100; // 100MB
    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd");

    // UAV 上传
    // platform\MySystem\src\com\tdream\web\SaveUAV.java
    /**
     * 上传数据及保存文件
     */
    @CrossOrigin
    @PostMapping(value = "api/saveUAV")
    @ResponseBody
    public void saveUAV(HttpServletRequest request, HttpSession session, HttpServletResponse response)throws ServletException,IOException {
        // 检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }
        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 中文处理
        upload.setHeaderEncoding("UTF-8");

        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        //String uploadPath = getServletContext().getRealPath("/") + File.separator + UPLOAD_DIRECTORY;
        //String uploadPath =this.getServletContext().getRealPath("/WEB-INF/upload");
        //System.out.println(this.getServletContext().getRealPath("/WEB-INF/upload"));
        String uploadPath ="C:\\Users\\LinZiXiang\\Desktop\\SpringbootVueGis\\src\\main\\resources\\META-INF\\resources\\UAV";
        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        try {
            // 解析请求的内容提取文件数据
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {

                        //就在这个地方，文件名的编码方式出现问题
                        String fileName = new File(item.getName()).getName();
                        //System.out.println("item.getName:"+item.getName());
                        System.out.println("获得的文件名："+fileName);
                        String filePath = uploadPath + File.separator + fileName;
                        System.out.println(filePath);
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        request.setAttribute("message",
                                "文件上传成功!");
                        //GPSgeter.runsql(filePath);
                        //将服务地址存入数据库
                        //获取图片的GPS坐标
                        //GPSgeter.printImageTags(filePath);
                        double latitude = GPSgeter.printImageTags(filePath)[0];
                        double longitude = GPSgeter.printImageTags(filePath)[1];
                        System.out.println(latitude);
                        System.out.println(longitude);
                        //准备sql指令
                        //将1个?占位符的实际参数,写在数组中
                        UavService uavService = null;
                        uavService.insert(fileName,latitude,longitude);
                        //调用QueryRunner类的方法update执行SQL语句，插入数据库
                        //然后使用/page/wcslist/wcslist.jsp页面查看
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message",
                    "错误信息: " + ex.getMessage());
        }
        // 跳转到 message.jsp
        //getServletContext().getRequestDispatcher("/page/wcslist/wcslist.jsp").forward(
        //request, response);
    }
    // --------------------------------------------------------------------------------

    // \platform\MySystem\src\com\tdream\web\UploadfileServlet.java
    /**
     * 上传数据及保存文件
     */

    @CrossOrigin
    @RequestMapping(value = "api/Uploadfile")
    @ResponseBody
    public String Uploadfile(MultipartFile file,HttpServletRequest request) throws IOException {
        if(Objects.isNull(file)||file.isEmpty()){
            System.out.println("null file");
            return "null";
        }
        String oldName = file.getOriginalFilename();
        String realPath = request.getServletContext().getRealPath("/upload");
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        file.transferTo(new File(folder,oldName));
        String format = sdf.format(new Date());
        String path = realPath + "\\" +oldName;
        System.out.println(path);
        String WCSurl = PublishWCS.wcs(path,oldName);
        System.out.println(WCSurl);
        wcsService.insert(WCSurl);
        String rivernet = "http://localhost:6080/arcgis/rest/services/WCS/" + WCSurl +"/ImageServer";
        return rivernet;
    }


    // --------------------------------------------------------------------------------
    // \platform\MySystem\src\com\tdream\web\FileServlet.java
    // rivernet.jsp 功能未完成？
    // 参数: file-上传文件 username-上传用户名 number-参数阈值
    @CrossOrigin
    @RequestMapping(value = "api/upload")
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file,@RequestParam("username")String username,@RequestParam("number")int number,HttpServletRequest request)throws IOException{
        System.out.println("////////////////////////////////// River Net Model ////////////////////////////////");
        // 存储上传的文件
        if(Objects.isNull(file)||file.isEmpty()){
            System.out.println("null file");
            return "null";
        }
        String oldName = file.getOriginalFilename();
        String realPath = request.getServletContext().getRealPath("/upload") + "\\" +username;
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        file.transferTo(new File(folder,oldName));
        String format = sdf.format(new Date());
        String path = realPath + "\\" +oldName;
        System.out.println("上传的文件保存的地址：");
        System.out.println(path);
//        String WCSurl = PublishWCS.wcs(path,oldName);
//        wcsService.insert(WCSurl);
//        System.out.println("发布的WCS服务：");
//        System.out.println(WCSurl);
//        String rivernet = "http://localhost:6080/arcgis/rest/services/WCS/" + WCSurl +"/ImageServer";
//        System.out.println("返回前端的WCS链接：");
//        System.out.println("rivernet:"+rivernet);
        return "rivernet";
    }

    // platform\MySystem\WebContent\page\wcslist\showUAVpicture.jsp
    // jsp代码中驱动查询数据库相应部分
    @CrossOrigin
    @RequestMapping(value = "api/findUavList")
    @ResponseBody
    public List<Uav> findUavList(HttpServletRequest request,HttpSession session){
        List<Uav> uavList = uavService.findUavList();
        return uavList;
    }

    // platform\MySystem\WebContent\page\wcslist\wcslist.jsp
    // jsp代码中驱动查询数据库相应部分
    @CrossOrigin
    @RequestMapping(value = "api/findWcsList")
    @ResponseBody
    public List<Wcs> findWcsList(HttpServletRequest request,HttpSession session){
        List<Wcs> wcsList = wcsService.findWcsList();
        return wcsList;
    }
}

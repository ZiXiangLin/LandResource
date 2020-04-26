package com.gis.demo.controller;

import com.gis.demo.domain.PersonalCenterNav;
import com.gis.demo.service.ManageMenuService;
import com.gis.demo.service.PerCenterNavService;
import com.gis.demo.ogc.Tool;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.beans.PropertyVetoException;
import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



@RestController
public class ManagerController {

    @Autowired
    ManageMenuService manageMenuService;
    @Autowired
    PerCenterNavService perCenterNavService;


    // 获取PerNAV内内容
    @CrossOrigin
    @PostMapping(value = "api/getPerNav")
    @ResponseBody
    public void getPerNav(HttpServletRequest request,HttpSession session){
        System.out.println("TEST: GER PER NAV");

    }


    /**
     * @author 邓村
     * @version 1.0
     * 2017年10月6日 16:00:18
     * 2020 年2月13日 林子翔
     * 该类用于处理页面管理员页面发送来的修改菜单项的请求
     * platform\MySystem\src\com\tdream\web\ManageMenuServlet.java
     */
    @CrossOrigin
    @PostMapping(value="api/ManageMenu")
    @ResponseBody
    public String ManageMenu(HttpServletRequest request, HttpSession session) throws SQLException {
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
        String method=""; String currentMenuName = "";
        String currentMenuID = ""; String newCnName = "";
        String newEnName = ""; String newIcon= "";
        String current_nav_name = "";
        String current_nav_icon = "";
        String current_nav_id = "";
        String parent_id = "";
        String parent_name = "";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(data));
            method = (String)jsonObject.get("method");
            currentMenuName = (String)jsonObject.get("currentMenuName");
            currentMenuID = (String)jsonObject.get("currentMenuID");
            newCnName = (String)jsonObject.get("newCnName");
            newEnName = (String)jsonObject.get("newEnName");
            newIcon = (String)jsonObject.get("newIcon");
            current_nav_name = (String)jsonObject.get("current_nav_name");
            current_nav_icon = (String)jsonObject.get("current_nav_icon");
            current_nav_id = (String)jsonObject.get("current_nav_id");
            parent_id = (String)jsonObject.get("parent_id");
            parent_name = (String)jsonObject.get("parent_name");
            //输出获取的参数
            System.out.println("method: " + method + "  currentMenuName: " + currentMenuName + "  currentMenuID: " + currentMenuID +
                    "  newCnName: " + newCnName+"  newIcon: "+newIcon + " newEnName: "+newEnName +" parentId: "+parent_id);
        }catch (ParseException e){
            e.printStackTrace();
        }
        boolean success = false;
        switch (method){
            case"addMenu":
                try {
                    success = manageMenuService.addMenu(newCnName,newEnName,newIcon);
                }catch (SQLException e){
                    e.printStackTrace();
                }
                break;
            case "updateMenu":
                try {
                    success = manageMenuService.updateMenu(currentMenuName,currentMenuID,newCnName,newEnName,newIcon);
                }catch (SQLException e){
                    e.printStackTrace();
                }
                break;
            case"removeMenu":
                try {
                    boolean isLeaf = manageMenuService.isLeaf(currentMenuName);
                    if(!isLeaf){
                        System.out.println("isleaf: " + isLeaf);
                    }else {
                        success = manageMenuService.removeMenu(currentMenuName,currentMenuID);
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
                break;
            case"addSiderNav":
                try {
                    success = manageMenuService.addSiderNav(parent_id,newCnName,newEnName,newIcon);
                }catch (SQLException e){
                    e.printStackTrace();
                }
                break;
            case "updateSiderNav":
                try {
                    success = manageMenuService.updateSiderNav(current_nav_id, current_nav_icon, newCnName, newEnName, newIcon);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                break;
            case"removeSiderNav":
                try {
                    boolean currentNavISLeaf = manageMenuService.currentNavIsLeaf(current_nav_id);
                    if (currentNavISLeaf){
                        success = manageMenuService.removeSiderNav(current_nav_name,current_nav_icon,current_nav_id);
                    }else {
                        System.out.println("currentNavIsLeaf:" + currentNavISLeaf);
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
        if (success!= true) return "false";
        else  return "true";
    }

    /**
     * Servlet implementation class ManagePersonalCenterNav 页面设计员处理个人中心页面增删改导航栏的请求
     *  platform\MySystem\src\com\tdream\web\ManagePersonalCenterNav.java
     */
    @CrossOrigin
    @PostMapping(value = "api/ManagePersonalCenterNav")
    @ResponseBody
    public String ManagePersonalCenterNav(HttpServletRequest request,HttpSession session){
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
        String method="";
        String cnname = "";
        String enname = "";
        String icon = "";
        String url = "";
        String oldcnname= "";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(data));
            method = (String)jsonObject.get("method");
            cnname = (String)jsonObject.get("cnname");
            enname = (String)jsonObject.get("enname");
            icon = (String)jsonObject.get("icon");
            url = (String)jsonObject.get("url");
            oldcnname = (String)jsonObject.get("oldcnname");
            //输出获取的参数
            System.out.println(method +"//" + cnname+"//" + enname+"//" + icon+"//" + url);
        }catch (ParseException e){
            e.printStackTrace();
        }
        String isSuccess = "true";
        try {
            switch (method) {
                case "addSiderNav":
                    System.out.println("addSiderNav");
                    perCenterNavService.addSiderNav(icon,cnname,enname,url);
                    break;
                case "updateSiderNav":
                    System.out.println("updateSiderNav");
                    perCenterNavService.updateSiderNav(icon,cnname,enname,url,oldcnname);
                    break;
                case "removeSiderNav":
                    System.out.println("removeSiderNav");
                    perCenterNavService.removeSiderNav(oldcnname);
                    break;
            }
        } catch (Exception e) {
            isSuccess="false";
            e.printStackTrace();
        }
        System.out.println(isSuccess);
        return isSuccess;
    }

    /**
     * Servlet implementation class PersonalCenterServlet
     * @author 邓村 2017年10月30日 20:20:01 处理个人中心的请求
     * 2020 年2月13日 林子翔
     */
    @CrossOrigin
    @PostMapping(value = "api/PersonalCenter")
    @ResponseBody
    public List<PersonalCenterNav> PersonalCenter(HttpServletRequest request,HttpSession session){
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
        String datatemp = sb.toString();
        String method="";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(datatemp));
            method = (String)jsonObject.get("method");
        }catch (ParseException e){
            e.printStackTrace();
        }
        List<PersonalCenterNav> list = null;
        try {
            switch (method){
                case"getSiderNav":
                    list = perCenterNavService.findPerCenterNavList();
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            return list;
        }
    }

    // ----------------------------------------------
    // NO usage found:
    // platform\MySystem\src\com\tdream\web\Webservice.java

    public String upload(HttpServletRequest request, HttpServletRequest response)throws ServletException, IOException, InterruptedException {
        //		String nav_name = request.getParameter("name");
        //		System.out.println(nav_name);
        //		//System.out.println(request.getParameter("formData"));
        //		//System.out.println(request.getParameter("formData"));
        // 得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
        //String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
        System.out.println("数据上传中...");
        System.out.println(".........");
        String username = request.getParameter("username");
        String filename="";
        String savePath = "";

        if(!username.isEmpty()){
            savePath = "C:\\Users\\LinZiXiang\\Desktop\\Dji"+"/"+username;
        }else{
            savePath = "C:\\Users\\LinZiXiang\\Desktop\\Dji";
        }
        String flowvalue = "";
        File file = new File(savePath);
        // 判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath + "目录不存在，需要创建");
            // 创建目录
            file.mkdir();
        }
        // 消息提示
        String message = "";
        try {
            // 使用Apache文件上传组件处理文件上传步骤：
            // 1、创建一个DiskFileItemFactory工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 2、创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 解决上传文件名的中文乱码
            upload.setHeaderEncoding("UTF-8");
            // 3、判断提交上来的数据是否是上传表单的数据
//			if (!ServletFileUpload.isMultipartContent(request)) {
//				// 按照传统方式获取数据
//				return;
//			}
            // 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
            List<FileItem> list = upload.parseRequest(request);
            //FileItem item = list.get(0);
            for (FileItem item:list){
                System.out.println("item:"+item);
                // 如果fileitem中封装的是普通输入项的数据
                if (item.isFormField()){
                    String name = item.getFieldName();
//					if(name.equals("flowvalue")){
//						flowvalue=item.getString("UTF-8");
//					}
//					// 解决普通输入项的数据的中文乱码问题
//					String value = item.getString("UTF-8");
                    // value = new String(value.getBytes("iso8859-1"),"UTF-8");
                    if(name.equals("number")){
                        flowvalue= item.getString("UTF-8");
                        System.out.println("test:"+flowvalue);
                    }
                    //System.out.println(name + "=" + value);
                }else {
                    // 如果fileitem中封装的是上传文件
                    // 得到上传的文件名称，
                    filename = item.getName();
                    System.out.println(filename);
                    if (filename == null || filename.trim().equals("")) {
                        continue;
                    }
                    // 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：
                    // c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
                    // 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    filename = filename.substring(filename.lastIndexOf("\\") + 1);
                    // 获取item中的上传文件的输入流
                    InputStream in = item.getInputStream();
                    // 创建一个文件输出流
                    FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
                    // 创建一个缓冲区
                    byte buffer[] = new byte[1024];
                    // 判断输入流中的数据是否已经读完的标识
                    int len = 0;
                    // 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                    while ((len = in.read(buffer)) > 0) {
                        // 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\"
                        // + filename)当中
                        out.write(buffer, 0, len);
                    }
                    // 关闭输入流
                    in.close();
                    // 关闭输出流
                    out.close();
                    // 删除处理文件上传时生成的临时文件
                    item.delete();
                    message = "文件上传与处理成功！";
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            message = "fail";
        }
        System.out.println(".........");
        System.out.println("数据上传结束");
        System.out.println("flow:"+flowvalue);
        System.out.println("username:"+username);
        System.out.println("filename:"+filename);
        System.out.println("fliepath:"+savePath+"/");
        System.out.println("数据处理中....");
        System.out.println(".........");
        String[] result = Tool.test(flowvalue,username,filename);
        System.out.println("请等待....。。");
        System.out.println(".........");
        TimeUnit.SECONDS.sleep(1);
        File scratch = new File(result[0]);
        String resultfile = "";
        String url= "";
        boolean t = false;
        do{
            String[] ff = scratch.list();
            System.out.print(ff[0]);
            for(String f : ff){
                if(f.endsWith("xml")){
                    resultfile = result[1];
                    t=false;
                    break;
                }else{
                    t=true;
                }
            }
            TimeUnit.SECONDS.sleep(20);
            System.out.println("等待中");
        }while(t);

        System.out.println("等待结束，开始执行");
        System.out.println(resultfile);
        System.out.println("发布地图服务中。。");
        url = Tool.wfs(resultfile);//接收地图服务的url
        System.out.println(url);
        System.out.println("地图服务发布完成");

        /*		String data = "";//"\""+
		data = "[{"+"\"message\":"+22+","+"\"url\":"+77+"}]"; */
        return message;
    }
}

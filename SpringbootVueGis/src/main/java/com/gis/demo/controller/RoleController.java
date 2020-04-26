package com.gis.demo.controller;

import com.gis.demo.domain.Role;
import com.gis.demo.domain.UserAvatar;
import com.gis.demo.domain.UserInfo;
import com.gis.demo.domain.EmailActive;
import com.gis.demo.domain.ModelParament.User;
import com.gis.demo.domain.ModelParament.UserInfoUpdate;
import com.gis.demo.domain.ModelParament.EmailCheck;

import com.gis.demo.service.*;

import com.gis.demo.ogc.PublishWCS;

import com.gis.demo.tools.SendEmail;
import com.gis.demo.tools.jps.GPSgeter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import com.gis.demo.domain.ModelParament.Result;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.apache.commons.codec.binary.Base64;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private EmailActiveService emailActiveService;
    @Autowired
    private UseravatarService useravatarService;
    @Autowired
    private WcsService wcsService;

    // 用户登陆
    // 对应部分： platform\MySystem\src\com\tdream\web\CheckLoginServlet.java
    @CrossOrigin
    @PostMapping(value = "api/login")
    @ResponseBody
    public Result login(@RequestBody User requestUser, HttpSession session) {
        String username = requestUser.getUsername();
        String password = requestUser.getPassword();
        username = HtmlUtils.htmlEscape(username);
        password = HtmlUtils.htmlEscape(password);
        System.out.println("username: "+username);
        System.out.println("password: "+password);
        Result result = new Result(200);
        Role user = roleService.findRoleByUsername(username);
        if(user != null) {
            if (Objects.equals(password,user.getPASSWORD())) {
                System.out.println("login");
                session.setAttribute("user",user);
                result.setCode(200);
                result.setLogin("pass");
                result.setAuthority(user.getAUTHORITY());
                EmailActive emailActive = emailActiveService.findByUsername(username);
                if(emailActive != null){
                    if(emailActive.getSTATUS().equals("1")){
                        result.setEmail("pass");
                    }else {
                        result.setEmail("fail");
                    }
                }
                return result;
            } else {
                result.setCode(400);
                return result;
            }
        }else {
            return new Result(400);
        }
    }
    // -------------------------------------------------------------------------------

    // 用户邮箱验证
    // 对应部分： platform\MySystem\src\com\tdream\web\EmailCheckServlet.java
    @CrossOrigin
    @PostMapping(value = "api/emailCheck")
    @ResponseBody
    public Result emailCheck(@RequestBody EmailCheck requestEmailCheck,HttpSession session){
        String op = requestEmailCheck.getOp();
        String actiCode = requestEmailCheck.getActiCode();
        String userName = requestEmailCheck.getUserName();
        Result result = null;
        if(op != null && !"".equals(op)){
            if("activate".equals(op)){
                EmailActive emailActive = emailActiveService.findByUsername(userName);
                Calendar c = Calendar.getInstance();
                if(emailActive != null){
                    long curent_time = c.getTimeInMillis();
                    long due_time = Long.parseLong(emailActive.getDUE_TIME());
                    if(actiCode.equals(emailActive.getACTICODE())){
                        if(curent_time < due_time){
                            emailActiveService.setStatus("1",userName);
                            result.setCode(200);
                            result.setRedirect("emailSuccess");
                            return result;
                        }else {
                            emailActiveService.deleteByUsername(userName);
                            roleService.deleteByUsername(userName);
                            userInfoService.deleteByUsername(userName);
                            result.setCode(400);
                            result.setRedirect("wrong");
                            return result;
                        }
                    }else {
                        emailActiveService.deleteByUsername(userName);
                        roleService.deleteByUsername(userName);
                        userInfoService.deleteByUsername(userName);
                        result.setCode(400);
                        result.setRedirect("wrong");
                        return result;
                    }
                }else {
                    result.setCode(400);
                    result.setRedirect("wrong");
                    return result;
                }
            }else {
                result.setCode(400);
                result.setRedirect("wrong");
                return result;
            }
        }
        return new Result(200);
    }
    // -------------------------------------------------------------------------------

    // 用户注册
    // 对应部分：platform\MySystem\src\com\tdream\web\RegisterServlet.java
    @CrossOrigin
    @PostMapping(value = "api/register")
    @ResponseBody
    public Result Register(HttpServletRequest request,HttpSession session){
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
        String userName = "";
        String password = "";
        String name = "";
        String homeAddress = "";
        String phone = "";
        String email = "";
        String method = "";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(datatemp));
            userName = (String)jsonObject.get("username");
            password = (String)jsonObject.get("password");
            name = (String)jsonObject.get("name");
            homeAddress = (String)jsonObject.get("homeAddress");
            phone = (String)jsonObject.get("phone");
            email = (String)jsonObject.get("email");
            method = (String)jsonObject.get("methods");

            System.out.println("param:" +userName+" "+password+" "+name+" "+homeAddress+" "+phone+" "+email+" "+method);
        }catch (ParseException e){
            e.printStackTrace();
        }
        Role role = null;
        String isSuccess="";
        Result result = new Result(200);
        try {
            switch (method){
                case "checkUserName":
                    role= roleService.findRoleByUsername(userName);
                    if (role != null){
                        isSuccess = "false";
                        result.setCode(400);
                    }
                    break;
                case "checkEmail":
                    role = roleService.findRoleByEmail(email);
                    if (role != null) {
                        isSuccess = "false";
                        result.setCode(400);
                    }
                    break;
                case "other":
                    if (role!=null){
                        isSuccess ="false";
                        result.setCode(400);
                    }else {
                        int dic_id = roleService.getDicId();
                        int id = roleService.getId();
                        roleService.insertRole(id,email,userName,password,1,dic_id);
                        userInfoService.insertUserInfo(userName,phone,homeAddress,email,name,null,"");
                    }
                    //send(request,response)
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
            isSuccess = "false";
            result.setCode(400);
        }
        return result;
    }
    private void send(HttpServletRequest request, HttpServletResponse response) throws IOException , SQLException , NamingException {
        //	1.数据库加两个字，state字段（0:未激活，1：激活成功），ActiCode:(放激活码)
        //	2.用户填写资料，插入数据成功，state字段默认是0,同时生成一个ActiCode也存入数据库
        //	3.提示用户激活。。。发送邮件。。。邮件中带一个激活成功页的URL，URL里有两个参数（1，用户ID，2：激活码）
        //	4.用户点击链接，回到激活成功页。。。激活成功页的Load事件，得到两个参数，以这两个参数为条件查询数据库里的数据，如果有，修改字段state为1,反之。。提示激活失败，重新激活。。
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line = null;
        StringBuilder sbtemp = new StringBuilder();
        try {
            while ((line = br.readLine())!=null){
                sbtemp.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String datatemp = sbtemp.toString();
        String userName = "";
        String email = "";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(datatemp));
            userName = (String)jsonObject.get("userName");
            email = (String)jsonObject.get("email");
        }catch (ParseException e){
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        //现在的时间(单位：毫秒)
        //TODO:时间换算问题，如何处理int和long之间的关系
        long time = c.getTimeInMillis();

        //创建激活码
        String actiCode=email+time;
        //过期时间为24小时后
        //	int token_exptime=(int)(time+1000*60*60*24);
        String dueTime=(time+1000*24*60*60)+"";    //这里测试是用的20秒
        emailActiveService.insert(userName,email,actiCode,dueTime,0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuffer sb=new StringBuffer("<div style=\"width:660px;overflow:hidden;border-bottom:1px solid #bdbdbe;\">"
                + "<div style=\"height:52px;overflow:hidden;border:1px solid #464c51;background:#353b3f;\">"
                + "</div>"+"<div style=\"padding:24px 20px;\">您好，"+email+"<br/>"
                + "<br/>欢迎使用国土资源应急和响应平台！<br/>"
                + "<br/>请点击下面链接激活账号，24小时生效，否则重新注册账号，链接只能使用一次，请尽快激活！</br>");
        sb.append("<a href=\"http://localhost:8080/test");
        sb.append(userName);
        sb.append("&actiCode=");
        sb.append(actiCode);
        sb.append("\">http://localhost:8080/test");
        sb.append(userName);
        sb.append("&actiCode=");
        sb.append(actiCode);
        sb.append("</a>"+"<br/>如果以上链接无法点击，请把上面网页地址复制到浏览器地址栏中打开<br/><br/><br/>注册平台<br/>"+sdf.format(new Date())+ "</div></div>" );

        SendEmail.send(email,sb.toString());
    }
    // --------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 30MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 100; // 100MB

    /**
     * @author 邓村
     * 时间 2017年10月27日11:03:47
     * 该类用于处理修改用户头像的请求
     */
    // platform\MySystem\src\com\tdream\web\SetUserAvatarServlet.java
    @CrossOrigin
    @PostMapping(value = "api/SetUserAvatar")
    @ResponseBody
    public String SetUserAvatar(HttpServletRequest request,HttpSession session){
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
        String userName = "";
        String imgDataURL = "";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(datatemp));
            userName = (String)jsonObject.get("userName");
            imgDataURL = (String)jsonObject.get("imgDataURL");
        }catch (ParseException e){
            e.printStackTrace();
        }
        System.out.println(imgDataURL);
        //import sun.misc.BASE64Encoder;
        //import sun.misc.BASE64Decoder;

        //return new  BASE64Encoder().encode(encrypted);
        //import org.apache.commons.codec.binary.Base64;
        //return Base64.encodeBase64String(encrypted); //编码

        //byte[] encrypted1 = new BASE64Decoder().decodeBuffer(text);
        //import org.apache.commons.codec.binary.Base64;
        //byte[] encrypted1 =Base64.decodeBase64(text);   //解码
        imgDataURL = imgDataURL.substring(22);
        String is="true";
        String realpath="";
        //图片文件存储路径
        //??????????
        String path=request.getSession().getServletContext().getRealPath("/")+"\\UserData\\"+userName+"\\"+userName+"_avatar.png";
        try {
            byte[] decodedBytes = Base64.decodeBase64(imgDataURL);
            File file=new File(path);
            //System.out.println("path:"+path);
            realpath=file.getPath();
            //System.out.println(file.getPath());
            if(!file.getParentFile().exists()){
                if(!file.getParentFile().mkdirs()){
                    System.out.println("创建目录失败");
                }else{
                    if(!file.exists()){
                        file.createNewFile();
                    }
                    FileOutputStream os = new FileOutputStream(file);
                    os.write(decodedBytes);
                    os.flush();
                    os.close();
                }
            }else{
                if(!file.exists()){
                    file.createNewFile();
                }
                FileOutputStream os=new FileOutputStream(file);
                os.write(decodedBytes);
                os.flush();
                os.close();
            }

            UserAvatar ua = useravatarService.findByUsername(userName);
            if(ua == null){
                useravatarService.insert(realpath,userName);
            }else {
                useravatarService.update(realpath,userName);
            }
        }catch (IOException e){
            e.printStackTrace();
            is = "false";
        }
        return is;
    }
    // --------------------------------------------------------------------------------


    // 用户信息更新
    @CrossOrigin
    @PostMapping(value = "api/userInfoUpdate")
    @ResponseBody
    public Result userInfoUpdate(@RequestBody UserInfoUpdate requestUser, HttpSession session) {
        System.out.println(requestUser);
        String username = requestUser.getUsername();
        String phone = requestUser.getPhone();
        String address = requestUser.getAddress();
        String email = requestUser.getEmail();
        String name = requestUser.getName();
        username = HtmlUtils.htmlEscape(username);
        phone = HtmlUtils.htmlEscape(phone);
        address = HtmlUtils.htmlEscape(address);
        email = HtmlUtils.htmlEscape(email);
        name = HtmlUtils.htmlEscape(name);
        System.out.println("username: "+username);
        System.out.println("phone: "+phone);
        System.out.println("address: "+address);
        System.out.println("email: "+email);
        System.out.println("name: "+name);
        String others="";
        String workplace="";
        UserInfo userinfo = userInfoService.findUserInfoByUsername(username);
        if(userinfo != null){
            userInfoService.updateUserInfo(username,phone,address,others,email,name,workplace);
            System.out.println("Update user info");
        }else {
            System.out.println("Insert user info");
            userInfoService.insertUserInfo(username,phone,address,others,email,name,workplace);
        }
        return new Result(200);
    }

    /**
     * Servlet implementation class ChangeInformationServlet
     * @author 邓村 时间 2017年10月23日20:33:26
     */
    // platform\MySystem\src\com\tdream\web\UserInformationServlet.java
    @CrossOrigin
    @PostMapping(value = "api/Userinfomation")
    @ResponseBody
    public String Userinfomation(HttpServletRequest request,HttpSession session){
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
        String userName = "";
        String name = "";
        String homeAddress = "";
        String phone = "";
        String email = "";
        String method = "";
        String others = "";
        String workplace = "";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(datatemp));
            userName = (String)jsonObject.get("userName");
            name = (String)jsonObject.get("name");
            homeAddress = (String)jsonObject.get("homeAddress");
            phone = (String)jsonObject.get("phone");
            email = (String)jsonObject.get("email");
            method = (String)jsonObject.get("method");
            others = (String)jsonObject.get("others");
            workplace = (String)jsonObject.get("workplace");
        }catch (ParseException e){
            e.printStackTrace();
        }
        String data= "true";
        UserInfo userInfo = userInfoService.findUserInfoByUsername(userName);
        try {
            if(method.equals("getInfo")){
                String currentName="";
                String currentPhone="";
                String currentOthers="";
                String currentEmail="";
                String currentHomeAddress="";
                if(userInfo.getNAME()!=null){
                    currentName=userInfo.getNAME();
                }
                if(userInfo.getPHONE()!=null){
                    currentPhone=userInfo.getPHONE();
                }
                if(userInfo.getOTHERS()!=null){
                    currentOthers=userInfo.getOTHERS();
                }
                if(userInfo.getEMAIL()!=null){
                    currentEmail=userInfo.getEMAIL();
                }
                if(userInfo.getHOMEADDRESS()!=null){
                    currentHomeAddress=userInfo.getHOMEADDRESS();
                }
                if(userInfo.getWORKPLACE()!=null){
                    workplace=userInfo.getWORKPLACE();
                }
                data="{"+"'name':'"+
                        currentName+
                        "','phone':'"+
                        currentPhone+
                        "','others':'"+
                        currentOthers+
                        "','email':'"+
                        currentEmail+
                        "','homeAddress':'"+
                        currentHomeAddress+
                        "','workplace':'"+
                        workplace+
                        "'}";
            }else {
                userInfoService.updateUserInfo(userName,phone,homeAddress,others,email,name,workplace);
                data="true";
            }
        }catch (Exception e){
            e.printStackTrace();
            data="false";
        }
        return data;
    }
    // ----------------------------------------------------------------------------------------------

    // 用户信息查询
    @CrossOrigin
    @PostMapping(value = "api/userInfo")
    @ResponseBody
    public UserInfo userInfo(@RequestBody User requestUser, HttpSession session) {
        String username = requestUser.getUsername();
        username = HtmlUtils.htmlEscape(username);
        System.out.println("username: "+username);
        UserInfo userinfo = userInfoService.findUserInfoByUsername(username);
        System.out.println("name: "+userinfo.getNAME());
        System.out.println("email: "+userinfo.getEMAIL());
        System.out.println("address: "+userinfo.getHOMEADDRESS());
        System.out.println("phone: "+userinfo.getPHONE());
        return userinfo;
    }

}

package com.gis.demo.controller;

import com.gis.demo.domain.*;
import com.gis.demo.domain.ModelParament.NavSearch;

import com.gis.demo.service.PerCenterNavService;
import com.gis.demo.tools.WCSprovider;
import com.gis.demo.tools.WPSexecuter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gis.demo.service.DicService;
import com.gis.demo.service.NavService;
import com.gis.demo.service.MenuService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class NavController {
    @Autowired
    private DicService dicService;
    @Autowired
    private NavService navService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private PerCenterNavService perCenterNavService;

    // 迭代查表生成Nav树的String
    // platform\MySystem\src\com\tdream \ utils \ IteratorUtil.java
    public StringBuilder MenuToHtml(String language){
        List<Nav> list = navService.findMenuList(1);
        boolean isFirst = true;
        StringBuilder html = new StringBuilder();
        String name = "";
        for(Nav nav:list){
            if(language.equals("en")) name= nav.getENNAME();
            else name = nav.getCNNAME();
            html.append("<li class=\"");
            if (isFirst)
                html.append("pf-nav-item current\" ");
            else
                html.append("pf-nav-item\" ");
            html.append("data-name=\""+name+"\" data-id=\""+nav.getNAV_ID()+"\">\n");
            html.append("<a href=\"javascript:;\" onclick=\"menuClick(\'"+nav.getNAV_ID()+"\')\">\n");
            html.append("<span class=\"iconfont\">"+nav.getNAV_ICON()+"</span>\n");
            html.append("<span class=\"pf-nav-title\">"+name+"</span>\n</a>\n</li>\n");
            isFirst=false;
        }
        return html;
    }
    public StringBuilder NavToHtml(String parentId,String language){
        List<Nav> navList = navService.findNavListByParentId(parentId);
        StringBuilder html= new StringBuilder();
        String name="";
        int treeNum=0;
        try {
            for (Nav nav:navList)
            {
                if (language.equals("en")){
                    name=nav.getENNAME();
                }
                else {
                    name=nav.getCNNAME();
                }
                html.append("<li>\n<a href=\'javascript:;\'>\n<span class=\'iconfont sider-nav-icon\' data-icon=\'"+
                        nav.getNAV_ICON().substring(1)+"\' data-id=\'"+nav.getNAV_ID()+"\'>&"+nav.getNAV_ICON().substring(1)+"</span>\n");
                html.append("<span class=\'sider-nav-title\'>"+name+"</span>\n");
                html.append("<i class='iconfont'>&#xe642;</i>\n</a>\n");
                if (nav.getISLEAF()!=1){
                    treeNum+=1;
                    html.append(subNavToHtml(nav.getNAV_ID(),treeNum,language));
                }
                html.append("</li>");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  html;
    }
    public StringBuilder subNavToHtml(String parentId,int treeNum,String language){
        List<Nav> subNavList=navService.findNavListByParentId(parentId);
        StringBuilder html=new StringBuilder("<div class=\'sider-nav-s\'>\n<ul id=\'tree"+
                treeNum+"\' class=\'easyui-tree\'>\n");
        String name="";
        for (Nav nav:subNavList){
            if (language.equals("en")){
                name=nav.getENNAME();
            }
            else {
                name=nav.getCNNAME();
            }
            if (nav.getISLEAF()==0){
                html.append("<li><span>"+name+"</span>");
                html.append(getNavHtml(nav.getNAV_ID(),language)+"</li>");
            }else if (nav.getISLEAF()==1){
                html.append("<li><span>"+name+"</span></li>");
            }
        }
        html.append("\n</ul></div>");
        return html;
    }
    public StringBuilder getNavHtml(String parentId,String language){
        List<Nav> navList=navService.findNavListByParentId(parentId);
        String name="";
        StringBuilder html=new StringBuilder("<ul>\n");
        for (Nav nav:navList){
            if (language.equals("en")){
                name=nav.getENNAME();
            }
            else {
                name=nav.getCNNAME();
            }
            if (nav.getISLEAF()==0){
                html.append("<li><span>"+name+"</span>"+getNavHtml(nav.getNAV_ID(),language)+"</li>\n");
            }else if (nav.getISLEAF()==1){
                html.append("<li><span>"+name+"</span></li>\n");
            }
        }html.append("</ul>");
        return html;
    }

    // Iterator generate Nav
    // platform\MySystem\src\com\tdream\web\IteratorServlet.java
    // -------------------------------------------------------------------
    @CrossOrigin
    @PostMapping(value = "api/menuToHtml")
    @ResponseBody
    public String menuToHtml(HttpServletRequest request,HttpSession session){
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
        String language=""; String html="";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(data));
            language = (String)jsonObject.get("language");
        }catch (ParseException e){
            e.printStackTrace();
        }
        try {
            html = MenuToHtml(language).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("menu to html"+html);
        return html;
    }
    @CrossOrigin
    @PostMapping(value = "api/navToHtml")
    @ResponseBody
    public String navToHtml(HttpServletRequest request,HttpSession session){
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
        String language=""; String parentId=""; String html="";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(data));
            language = (String)jsonObject.get("language");
            parentId = (String)jsonObject.get("parentId");
        }catch (ParseException e){
            e.printStackTrace();
        }
        System.out.println("language:"+language+" parentId: "+parentId);
        try {
            html = NavToHtml(parentId,language).toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("nav to html:"+html);
        return html;
    }

    // Managers' operations toward Nav/Dic node
    // platform\MySystem\src\com\tdream\web\NavServlet.java
    // ------------------------------------------------------------------

    @CrossOrigin
    @PostMapping(value = "api/findNavListByParentId")
    @ResponseBody
    public List<Nav> findNavListByParentId(HttpServletRequest request,HttpSession session){
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
        String parentId="";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(data));
            parentId = (String)jsonObject.get("parentId");
        }catch (ParseException e){
            e.printStackTrace();
        }
        List<Nav> navList = navService.findNavListByParentId(parentId);
        return navList;
    }

    @CrossOrigin
    @PostMapping(value = "api/findNavInfoByEnname")
    @ResponseBody
    public Nav findNavInfoByEnname(HttpServletRequest request,HttpSession session){
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
        String enname="";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(data));
            enname = (String)jsonObject.get("enname");
        }catch (ParseException e){
            e.printStackTrace();
        }
        Nav nav = navService.findNavInfoByEnname(enname);
        return nav;
    }

    @CrossOrigin
    @PostMapping(value = "api/findNavInfoByCnname")
    @ResponseBody
    public Nav findNavInfoByCnname(HttpServletRequest request,HttpSession session){
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
            cnname = (String)jsonObject.get("cnname");
        }catch (ParseException e){
            e.printStackTrace();
        }
        Nav nav = navService.findNavInfoByCnname(cnname);
        return nav;
    }

    @CrossOrigin
    @PostMapping(value = "api/deleteNode")
    @ResponseBody
    public void deleteNode(HttpServletRequest request,HttpSession session){
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
        String nav_id="";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(data));
            nav_id = (String)jsonObject.get("nav_id");
        }catch (ParseException e){
            e.printStackTrace();
        }
        delete(nav_id);
    }

    public void delete(String nav_id){
        Nav nav = navService.findNavInfoByNavId(nav_id);
        if(nav.getISLEAF() == 0){
            List<Nav> navList = navService.findNavListByParentId(nav_id);
            for (int i = 0; i < navList.size(); i++) {
                delete(navList.get(i).getNAV_ID());
            }
            navService.deleteNode(nav_id);
        }else {
            navService.deleteNode(nav_id);
            System.out.println("deleteNode: "+nav.getCNNAME());
        }
        List<Nav> navList1 = navService.findNavListByParentId(nav.getPARENT_ID());
        if(navList1.size() == 0){
            navService.setIsLeafByNavId(nav.getPARENT_ID());
        }
    }

    @CrossOrigin
    @PostMapping(value = "api/updateNode")
    @ResponseBody
    public void updateNode(HttpServletRequest request,HttpSession session){
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
        String nav_id="";
        String cnname_changed = "";
        String enname_changed = "";
        String url_changed = "";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(data));
            nav_id = (String)jsonObject.get("nav_id");
            cnname_changed = (String)jsonObject.get("cnname_changed");
            enname_changed = (String)jsonObject.get("enname_changed");
            url_changed = (String)jsonObject.get("url_changed");
        }catch (ParseException e){
            e.printStackTrace();
        }
        navService.updateNodeCN(nav_id,cnname_changed,enname_changed,url_changed);
    }

    @CrossOrigin
    @PostMapping(value = "api/addNode")
    @ResponseBody
    public String addNode(HttpServletRequest request,HttpSession session){
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
        String nav_cnname="";
        String nav_enname = "";
        String nav_url = "";
        String parent_id = "";
        String parent_level = "";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(data));
            nav_cnname = (String)jsonObject.get("nav_cnname");
            nav_enname = (String)jsonObject.get("nav_enname");
            nav_url = (String)jsonObject.get("nav_url");
            parent_id = (String)jsonObject.get("parent_id");
            parent_level = (String)jsonObject.get("parent_level");
        }catch (ParseException e){
            e.printStackTrace();
        }
        String error = "";
        error += navService.addNode(nav_cnname,nav_enname,nav_url,parent_id,parent_level);
        if (error=="")System.out.println("addNode:"+nav_cnname);
        return error;
    }

    //------------------------------------------------------------------------------

    // platform\MySystem\src\com\tdream\web\SearchFunctionServlet.java
    /**
     * Servlet implementation class SearchFunctionServlet
     * 处理主页面上搜索功能的请求
     * @author 邓村
     * 2017年11月3日17:00:37
     */
    @CrossOrigin
    @PostMapping(value = "api/SearchFunction")
    @ResponseBody
    public String SearchFunction(HttpServletRequest request,HttpSession session){
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
        String word="";
        String method = "";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(datatemp));
            word = (String)jsonObject.get("word");
            method = (String)jsonObject.get("method");
        }catch (ParseException e){
            e.printStackTrace();
        }
        System.out.println(word);
        StringBuffer data=new StringBuffer();
        if (method.equals("findOne")){
            if(!word.equals("")&&word!=null){
                try {
                    Dic dic = dicService.findByCnname(word);
                    Nav nav = navService.findByDicId(dic.getID());
                    String parentID = "";
                    parentID = findParentID(nav.getNAV_ID());
                    data.append("{\"allParentID\":[\"");
                    data.append(parentID);
                    while(!parentID.equals("")){
                        parentID=findParentID(parentID);
                        data.append("\",\""+parentID);
                    }
                    data.append("\"]}");
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }else {
                data.append("{\"allParentID\":[]}");
            }
        }else {
            if(!word.equals("")&&word!=null){
                List<Dic> cn = dicService.findByCnnameLike(word);
                data.append("{\"result\":[");
                for(int i=0;i<cn.size();i++){
                    if(i==(cn.size()-1)){
                        data.append("\""+cn.get(i).getCNNAME()+"\"");
                    }else{
                        data.append("\""+cn.get(i).getCNNAME()+"\",");
                    }

                }
                data.append("]}");
            }else{
                data.append("{\"result\":[]}");
            }
        }
        return data.toString();
    }

    private String findParentID(String currentID)throws SQLException{
        String result = "";
        Nav nav = navService.findByNavId(currentID);
        if (nav!=null){
            result = nav.getPARENT_ID();
        }
        return result;
    }

    // -----------------------------------------------------------------------------

    //根据NavLevel 获得同层的NavBar
    @CrossOrigin
    @PostMapping(value = "api/getNav")
    @ResponseBody
    public List<Dic> findByNavLevel(@RequestBody NavSearch navSearch, HttpSession session) {
        System.out.println("---------------------FindByNavLevel: "+ navSearch.getNavLevel() +"----------------------------");
        List<Nav> nav = navService.findByNavLevel(navSearch.getNavLevel());
        int dic_id = 1;
        List<Dic> dicList = new ArrayList<Dic>();
        for(Nav attribute : nav){
            System.out.println("dic id: "+attribute.getDIC_ID());
            dic_id = attribute.getDIC_ID();
            Dic dic = dicService.findById(dic_id);
            dicList.add(dic);
        }
        return  dicList;
    }

    //根据Dic_id 获得下一层的NavBar
    @CrossOrigin
    @PostMapping(value = "api/getNavByDicId")
    @ResponseBody
    public List<Dic> findByDicId(@RequestBody NavSearch navSearch, HttpSession session) {
        System.out.println("---------------------FindByDicID "+ navSearch.getDicId() +"-----------------------------");
        Nav nav_current = navService.findByDicId(navSearch.getDicId());
        System.out.println("nav ID: "+nav_current.getNAV_ID());
        System.out.println("nav cnname: "+ nav_current.getCNNAME());
//        String parent_id = "";
//        parent_id = nav_current.getNAV_ID().substring(0,2)+"00000000";
//        System.out.println("parent_id : "+parent_id);
        List<Nav> nav = navService.findNavListFormatByParentId(nav_current.getNAV_ID());
        int dic_id = 1;
        List<Dic> dicList = new ArrayList<Dic>();
        for(Nav attribute : nav){
            System.out.println("dic id: "+attribute.getDIC_ID());
            dic_id = attribute.getDIC_ID();
            Dic dic = dicService.findById(dic_id);
            dicList.add(dic);
        }
        return  dicList;
    }

    //根据Dic_id 判断ISLEAF
    @CrossOrigin
    @PostMapping(value = "api/isLeaf")
    @ResponseBody
    public Nav isLeaf(@RequestBody NavSearch navSearch, HttpSession session) {
        System.out.println("-------------------------ISLEAF----------------------------");
        Nav nav_current = navService.findByDicId(navSearch.getDicId());
        System.out.println("nav ID: "+nav_current.getNAV_ID());
        System.out.println("is leaf:"+nav_current.getISLEAF());
        return nav_current;
    }

    //根据DIC ID 查找nav信息
    @CrossOrigin
    @PostMapping(value = "api/findNavInfoByDicId")
    @ResponseBody
    public Nav findNavInfoByDicId(HttpServletRequest request,HttpSession session){
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
        String dic ="";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(datatemp));
            dic = (String)jsonObject.get("dic");
        }catch (ParseException e){
            e.printStackTrace();
        }
        System.out.println("dic id:"+dic);
        int dic_id = Integer.parseInt(dic);
        Nav navResult = navService.findNavInfoByDicId(dic_id);
        return navResult;
    }
    @CrossOrigin
    @PostMapping(value = "api/findDicInfoByDicId")
    @ResponseBody
    public Dic findDicInfoByDicId(HttpServletRequest request,HttpSession session){
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
        String dic ="";
        try {
            JSONObject jsonObject = (JSONObject) (new JSONParser().parse(datatemp));
            dic = (String)jsonObject.get("dic");
        }catch (ParseException e){
            e.printStackTrace();
        }
        System.out.println("dic id:"+dic);
        int dic_id = Integer.parseInt(dic);
        Dic dicResult = dicService.findById(dic_id);
        return dicResult;
    }

    // --------------------------------------------------------------------
    // Index
    // platform\MySystem\src\com\tdream\web\IndexServlet.java
    @CrossOrigin
    @PostMapping(value = "api/index")
    @ResponseBody
    public List<Dic> index(HttpServletRequest request, HttpSession session){
        List<Nav>  menuList = navService.findMenuList(1);
        System.out.println(menuList);
        int dic_id = 1;
        List<Dic> dicList = new ArrayList<Dic>();
        for(Nav attribute : menuList){
            System.out.println("dic id: "+attribute.getDIC_ID());
            dic_id = attribute.getDIC_ID();
            Dic dic = dicService.findById(dic_id);
            dicList.add(dic);
        }
        return dicList;
    }

    @CrossOrigin
    @PostMapping(value = "api/getNavList")
    @ResponseBody
    public List<Nav> getNavList(HttpServletRequest request,HttpSession session){
        List<Nav> navList = navService.findNavList();
        for(Nav attribute : navList){
            Dic dic = dicService.findById(attribute.getDIC_ID());
            if(dic!=null){
                attribute.setCNNAME(dic.getCNNAME());
                attribute.setENNAME(dic.getENNAME());
            }
        }
        return navList;
    }

    @CrossOrigin
    @PostMapping(value = "api/getMenuList")
    @ResponseBody
    public List<Menu> getMenuList(HttpServletRequest request,HttpSession session){
        List<Menu> menuList = menuService.findMenuList();
        for(Menu attribute : menuList){
            Dic dic = dicService.findById(attribute.getDIC_ID());
            if(dic!=null){
                attribute.setCNNAME(dic.getCNNAME());
                attribute.setENNAME(dic.getENNAME());
            }
        }
        return menuList;
    }

    @CrossOrigin
    @PostMapping(value = "api/getPerCenterNavList")
    @ResponseBody
    public List<PersonalCenterNav> getPerCenterNavList(HttpServletRequest request,HttpSession session){
        List<PersonalCenterNav> personalCenterNavList = perCenterNavService.getPerCenterNavList();
        return personalCenterNavList;
    }
}

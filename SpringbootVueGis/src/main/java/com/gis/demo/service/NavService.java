package com.gis.demo.service;

import com.gis.demo.mapper.DicDao;
import com.gis.demo.mapper.NavDao;
import com.gis.demo.domain.Nav;
import com.gis.demo.domain.Dic;

import com.gis.demo.tools.CommonUtils;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NavService {
    @Autowired
    private NavDao navDao;
    @Autowired
    private DicDao dicDao;

    public List<Nav> findNavList(){return navDao.findNavList();}

    public List<Nav> findByNavLevel(int navLevel){return navDao.findByNavLevel(navLevel);}

    public Nav findByDicId(int dicId){return navDao.findByDicId(dicId);}

    public Nav findByNavId(String id){
        return navDao.findByNavId(id);
    }

    // platform\MySystem\src\com\tdream\service\NavService.java
    public List<Nav> findMenuList(int nav_level){
        return navDao.findMenuList(nav_level);
    }

    public int findMaxLevel(){
        return navDao.findMaxLevel();
    }

    public List<Nav> findNavListByParentId(String parent_id){
        return navDao.findNavListByParentId(parent_id);
    }

    public List<Nav> findNavListFormatByParentId(String parent_id){ return navDao.findNavListFormatByParentId(parent_id) ;}

    public Nav findNavInfoByCnname(String cnname){
        return navDao.findNavInfoByCnname(cnname);
    }

    public Nav findNavInfoByEnname(String enname){
        return navDao.findNavInfoByEnname(enname);
    }

    public Nav findNavInfoByDicId(int dic_id){return navDao.findNavInfoByDicId(dic_id);}

    public void deleteNode(String nav_id){
        Nav nav = navDao.findByNavId(nav_id);
        int dic_id = nav.getDIC_ID();
        dicDao.deleteByDicId(dic_id);
    }

    public void updateNodeCN(String nav_id, String cnname_changed, String enname_changed, String url_changed){
        navDao.updateNode(nav_id,cnname_changed,enname_changed,url_changed);
    }

    public String makeNavId(String parent_id,String parent_level){
        int parentLevel = Integer.valueOf(parent_level);
        String unCompletedId = parent_id.substring(0, parentLevel * 2);
        List<Nav> navList= navDao.makeNavId(unCompletedId + "%");
        ArrayList<String> idList = new ArrayList<String>();
        for (int i = 0; i < navList.size(); i++) {
            idList.add(navList.get(i).getNAV_ID().substring(parentLevel * 2, parentLevel * 2 + 2));
        }
        String navId = CommonUtils.getUUID();
        String end0 = "";
        int a = 10 - parentLevel * 2 - 2;
        for (int k = 0; k < a; k++) {
            end0 += "0";
        }
        if (idList.size() > 0) {
            int temp = 0;
            outer1: for (int i = 1; i < 100; i++) {
                outer2:
                for (int j = 0; j < idList.size(); j++) {

                    if (Integer.valueOf(idList.get(j)) == i) {
                        continue outer1;
                    }else{
                        continue outer2;
                    }
                }
                temp = i;
                break;
            }
            if (temp < 10) {
                navId = unCompletedId + "0" + temp + end0;
            } else {
                navId = unCompletedId + temp + end0;
            }
        } else {
            navId = unCompletedId + "01" + end0;
        }
        return navId;
    }

    public String printError(Dic dic){
        Nav nav = navDao.findByDicId(dic.getID());
        String result="在";
        int i=nav.getNAV_LEVEL();
        for(;i>0;i--){
            StringBuilder currentId=new StringBuilder(nav.getNAV_ID());
            String str="";
            for(int j=0;j<i;j++){str+="00";}
            Nav nav1=findNavInfoByNavId(currentId.replace(currentId.length()-(i+1)*2,currentId.length()-2,str ).toString());
            Dic dic1 = dicDao.findById(nav1.getDIC_ID());
            if (i==1)result+=dic1.getCNNAME()+"节点已存在";
            else result+=dic1.getCNNAME()+"下";
        }
        return result;
    }

    public String addNode(String nav_cnname,String nav_enname, String nav_url, String parent_id, String parent_level){
        Dic dic1 = dicDao.findByCnname(nav_cnname);
        if (dic1!=null){
            return printError(dic1);
        }else {
            dicDao.insert(9999,"导航类",nav_cnname,nav_enname);
            Dic dic = dicDao.findByCnname(nav_cnname);
            String nav_id = makeNavId(parent_id,parent_level);
            int nav_level = Integer.valueOf(parent_level)+1;
            navDao.insert(nav_id,dic.getID(),null,null,nav_url,nav_level,parent_id,1);
            navDao.setIsLeafByNavId(parent_id,0);
        }
        return null;
    }

    public Nav findNavInfoByNavId(String nav_id){
        return navDao.findNavInfoByNavId(nav_id);
    }

    public void setIsLeafByNavId(String nav_id){
        navDao.setIsLeafByNavId(nav_id,1);
    }
}

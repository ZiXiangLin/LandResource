package com.gis.demo.service;


import com.gis.demo.domain.Dic;
import com.gis.demo.domain.Menu;
import com.gis.demo.domain.Nav;
import com.gis.demo.mapper.DicDao;
import com.gis.demo.mapper.MenuDao;
import com.gis.demo.mapper.NavDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ManageMenuService {

    @Autowired
    MenuDao menuDao;
    @Autowired
    NavDao navDao;
    @Autowired
    DicDao dicDao;

    // platform\MySystem\src\com\tdream\manage\ManageMenu.java

    public String getMenuId(){
        int count = menuDao.findMenuList().size();
        String menuid = "0" + (count + 1) + "00000000";
        return menuid;
    }

    public String getNavId(String parent_id){
        List<Nav> list = navDao.findNavListByParentId(parent_id);
        String nav_id = parent_id.substring(0, 2) + "0" + (list.size() + 1) + "000000";
        System.out.println("parent level: " + parent_id.substring(0, 1) + "   nav_id: " + nav_id);
        return nav_id;
    }

    public boolean isLeaf(String currentMenuName){
        Dic dic = dicDao.findByCnname(currentMenuName);
        Nav nav = navDao.findByDicId(dic.getID());
        System.out.println("leaf:" + nav.getISLEAF());
        if (nav.getISLEAF() == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 设置该id的项是否为叶结点
     * @param nav_id
     * @param i      1表示是叶，0表示不是
     */
    public void setIsLeafByNavId(String nav_id,int i){
        navDao.updateByNavId(nav_id,i);
    }

    /**
     * 判断该id下的nav是不是叶结点
     * @param nav_id
     * @return
     * @throws SQLException
     */
    public boolean currentNavIsLeaf(String nav_id)throws SQLException{
        Nav nav = navDao.findByNavId(nav_id);
        System.out.println("currentNavIsLeaf: " + nav.getISLEAF());
        if (nav.getISLEAF() == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取dicid
     */
    public int getDicId(){
        int dicId = 0;
        Object object = navDao.findMaxDicId();
        int maxDicId = Integer.parseInt(String.valueOf(object));
        return maxDicId + 1;
    }

    /**
     * 在添加菜单
     * @throws SQLException
     */
    public boolean addMenu(String newCnName,String newEnName, String newIcon)
            throws SQLException {
        Dic dic = dicDao.findByCnname(newCnName);
        if( !(dic == null)){
            System.out.println("dic is not null");
            return false;
        }else {
            dicDao.insert(getDicId(),"菜单类",newCnName,newEnName);
            menuDao.insert(getMenuId(),getDicId(),newIcon);
            navDao.insert(getMenuId(),getDicId(),newIcon,"'javascript:;'","'javascript:;'",1,null,1);
            return true;
        }
    }

    /**
     * 修改菜单
     *
     * @throws SQLException
     */
    public boolean updateMenu(String currentMenuName, String currentMenuID, String newCnName, String newEnName, String newIcon) throws SQLException {
       Dic dic = dicDao.findByCnname(currentMenuName);
       menuDao.updateMenuIcon(newIcon,dic.getID());
       navDao.updateIcon(newIcon,dic.getID());
       dicDao.update(newCnName,newEnName,dic.getID());
       return true;
    }

    /**
     * 删除菜单
     *
     * @throws SQLException
     */
    public boolean removeMenu(String currentMenuName, String currentMenuID) throws SQLException {
        Dic dic = dicDao.findByCnname(currentMenuName);
        if(dic == null){
            return false;
        }else {
            menuDao.deleteByDicId(dic.getID());
            navDao.deleteByDicId(dic.getID());
            dicDao.deleteByDicId(dic.getID());
        }
        return true;
    }

    /**
     * 添加2级导航栏（菜单栏为1级）
     *
     * @return
     * @throws SQLException
     */
    public boolean addSiderNav( String parent_id, String newCnName, String newEnName, String newIcon) throws SQLException {
        String nav_id = getNavId(parent_id);
        Dic dic = dicDao.findByCnname(newCnName);
        if(!(dic ==null)) {
            return false;
        }else {
            int dic_id = getDicId();
            dicDao.insert(dic_id,"导航类",newCnName,newEnName);
            navDao.insert(nav_id,dic_id,newIcon,"javascript:;", "javascript:;", 2, parent_id, 1);
            return true;
        }

    }

    /**
     * 修改2级导航栏（菜单栏为1级）
     *
     * @return
     * @throws SQLException
     */
    public boolean updateSiderNav(String current_nav_id, String current_nav_icon, String newCnName, String newEnName, String newIcon) throws SQLException {
        Nav nav = navDao.findByNavId(current_nav_id);
        navDao.updateIconByNavId(newIcon,current_nav_id);
        dicDao.update(newCnName,newEnName,nav.getDIC_ID());
        return true;
    }

    /**
     * 删除2级导航栏（菜单栏为1级）
     *
     * @return
     * @throws SQLException
     */
    public boolean removeSiderNav(String current_nav_name, String current_nav_icon, String current_nav_id) throws SQLException {
        Nav nav = navDao.findByNavId(current_nav_id);
        if (nav == null){
            return false;
        }else {
            navDao.deleteByDicId(nav.getDIC_ID());
            dicDao.deleteByDicId(nav.getDIC_ID());
//            System.out.println("dicid:" + nav.getDIC_ID());
            String parent_id = "";
            parent_id = current_nav_id.substring(0, 2) + "00000000";
            if (navDao.findNavListByParentId(parent_id).size() < 1) {
                setIsLeafByNavId(parent_id, 1);
            }
        }
        return true;
    }
}

package com.gis.demo.service;

import com.gis.demo.domain.PersonalCenterNav;
import com.gis.demo.mapper.PerCenterNavDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerCenterNavService {

    @Autowired
    private PerCenterNavDao perCenterNavDao;

    public List<PersonalCenterNav> getPerCenterNavList(){return perCenterNavDao.getPerCenterNavList();}

    public void addSiderNav(String icon,String cnname,String enname,String url){
        perCenterNavDao.addSiderNav(icon,cnname,enname,url);
    }

    public void updateSiderNav(String icon,String cnname,String enname,String url,String oldcnname){
        perCenterNavDao.updateSiderNav(icon,cnname,enname,url,oldcnname);
    }

    public void removeSiderNav(String oldcnname){
        perCenterNavDao.removeSiderNav(oldcnname);
    }

    public List<PersonalCenterNav> findPerCenterNavList(){
        return perCenterNavDao.findPerCenterNavList();
    }
}

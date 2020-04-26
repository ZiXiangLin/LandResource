package com.gis.demo.service;

import com.gis.demo.domain.Menu;
import com.gis.demo.mapper.MenuDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    @Autowired
    MenuDao menuDao;

    public List<Menu> findMenuList(){
        return menuDao.findMenuList();
    }
}

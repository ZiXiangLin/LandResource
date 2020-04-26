package com.gis.demo.service;

import com.gis.demo.domain.Dic;
import com.gis.demo.mapper.DicDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DicService {
    @Autowired
    private DicDao dicDao;

    public Dic findById(int id){ return dicDao.findById(id); }

    public Dic findByCnname(String cnname){ return dicDao.findByCnname(cnname);}

    public List<Dic> findDicList(){
        List<Dic> dicList = dicDao.findDicList();
        return dicList;
    }

    public List<Dic> findByCnnameLike(String cnname){
        return dicDao.findByCnnameLike(cnname);
    }
}

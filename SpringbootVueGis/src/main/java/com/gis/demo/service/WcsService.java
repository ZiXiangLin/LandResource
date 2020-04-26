package com.gis.demo.service;

import com.gis.demo.domain.Wcs;
import com.gis.demo.mapper.WcsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WcsService {

    @Autowired
    WcsDao wcsDao;

    public void insert(String wcsUrl){
        Object object = wcsDao.getId();
        int maxId = Integer.parseInt(String.valueOf(object))+1;
        wcsDao.insert(maxId,wcsUrl);
    }

    public List<Wcs> findWcsList(){
        return wcsDao.findWcsList();
    }

}

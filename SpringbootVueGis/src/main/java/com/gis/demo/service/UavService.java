package com.gis.demo.service;

import com.gis.demo.domain.Uav;
import com.gis.demo.mapper.UavDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UavService {

    @Autowired
    UavDao uavDao;

    public void insert(String uavImageFileDir,double latitude,double longitude){
        Object object = uavDao.getId();
        int maxId = Integer.parseInt(String.valueOf(object))+1;
        uavDao.insert(maxId,uavImageFileDir,latitude,longitude);
    }

    public List<Uav> findUavList(){
        return uavDao.findUavList();
    }
}

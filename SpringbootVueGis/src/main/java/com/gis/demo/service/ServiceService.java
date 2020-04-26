package com.gis.demo.service;

import com.gis.demo.mapper.ServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceService {

    @Autowired
    private ServiceDao serviceDao;

    public List<com.gis.demo.domain.Service> findByType(String type){
        return serviceDao.findByType(type);
    }

    public com.gis.demo.domain.Service findByCnname(String cnname){
        return serviceDao.findByCnname(cnname);
    }
}

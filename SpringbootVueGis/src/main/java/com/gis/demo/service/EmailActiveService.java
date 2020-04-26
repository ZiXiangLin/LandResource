package com.gis.demo.service;

import com.gis.demo.domain.EmailActive;
import com.gis.demo.mapper.EmailActiveDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailActiveService {

    @Autowired
    private EmailActiveDao emailActiveDao;

    public EmailActive findByUsername(String username){
       return emailActiveDao.findByUsername(username);
    }

    public void setStatus(String status,String username){
        emailActiveDao.setStatus(status,username);
    }

    public void deleteByUsername(String username){
        emailActiveDao.deleteByUsername(username);
    }

    public void insert(String username,String email,String actiCode,String due_time,int status){
        emailActiveDao.insert(username,email,actiCode,due_time,status);
    }
}

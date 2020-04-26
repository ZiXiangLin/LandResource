package com.gis.demo.service;

import com.gis.demo.domain.UserAvatar;
import com.gis.demo.mapper.UseravatarDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UseravatarService {

    @Autowired
    UseravatarDao useravatarDao;

    public UserAvatar findByUsername(String username){
        return useravatarDao.findByUsername(username);
    }

    public void insert(String useravatar,String username){
        useravatarDao.insert(useravatar,username);
    }

    public void update(String useravatar,String username){
        useravatarDao.update(useravatar,username);
    }

}

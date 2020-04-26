package com.gis.demo.service;

import com.gis.demo.domain.UserInfo;
import com.gis.demo.mapper.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    @Autowired
    UserInfoDao userInfoDao;

    public UserInfo findUserInfoByUsername(String username){
        return userInfoDao.findUserInfoByUsername(username);
    }

    public void insertUserInfo(String username,String phone,String homeAddress,String others,String email,String name,String workplace){
        userInfoDao.insertUserinfo(username,phone,homeAddress,others,email,name,workplace);
    }

    public void updateUserInfo(String username,String phone,String homeAddress,String others,String email,String name,String workplace){
        userInfoDao.updateUserinfo(username,phone,homeAddress,others,email,name,workplace);
    }

    public void deleteByUsername(String username){
        userInfoDao.deleteByUsername(username);
    }
}



package com.gis.demo.service;

import com.gis.demo.domain.Role;
import com.gis.demo.mapper.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    public List<Role> roleList(){
        return roleDao.roleList();
    }

    public Role findRoleByUsername(String username){
        return roleDao.findRoleByUsername(username);
    }

    public Role findRoleByEmail(String email){ return roleDao.findRoleByEmail(email);}

    public int getDicId(){
        int dicId = 0;
        Object object = roleDao.getDicId();
        int maxDicId = Integer.parseInt(String.valueOf(object));
        return maxDicId + 1;
    }

    public int getId(){
        int Id = 0;
        Object object = roleDao.getId();
        int maxId = Integer.parseInt(String.valueOf(object));
        return maxId + 1;
    }

    public int insertRole(int id,String email,String username,String password,int authority,int dicId){
        roleDao.insertRole(id,email,username,password,authority,dicId);
        return 1;
    }

    public void deleteByUsername(String username){
        roleDao.deleteByUsername(username);
    }
}

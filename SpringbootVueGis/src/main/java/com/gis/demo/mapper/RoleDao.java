package com.gis.demo.mapper;

import java.util.List;

import com.gis.demo.domain.Role;
import org.apache.ibatis.annotations.*;


@Mapper
public interface RoleDao {

    @Select("select * from role ")
    List<Role> roleList();

    @Select("select * from role where USERNAME=#{username}")
    Role findRoleByUsername(@Param("username") String username);

    @Select("select * from role where EMAIL = #{email}")
    Role findRoleByEmail(@Param("email")String email);

    @Select("select max(DIC_ID) from role")
    int getDicId();

    @Select("select max(ID) from role")
    int getId();

    @Insert("insert into role values(#{id},#{email},#{username},#{password},#{authority},#{dicId})")
    void insertRole(@Param("id")int id,@Param("email") String email,@Param("username") String username,@Param("password") String password,@Param("authority") int authority,@Param("dicId")int dicId);

    @Delete("delete from role where USERNAME = #{username}")
    void deleteByUsername(@Param("username")String username);
}

package com.gis.demo.mapper;

import com.gis.demo.domain.UserAvatar;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UseravatarDao {

    @Select("select * from useravatar where USERNAME = #{username}")
    UserAvatar findByUsername(@Param("username") String username);

    @Insert("insert into useravatar values(#{useravatar},#{username})")
    void insert(@Param("useravatar")String useravatar,@Param("username")String username);

    @Update("update useravatar set USERAVATAR = #{useravatar} where USERNAME = #{username}")
    void update(@Param("useravatar")String useravatar,@Param("username")String username);
}

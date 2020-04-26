package com.gis.demo.mapper;

import org.apache.ibatis.annotations.*;
import com.gis.demo.domain.EmailActive;

@Mapper
public interface EmailActiveDao {

    @Select("select * from email_active where USERNAME = #{username}")
    EmailActive findByUsername(@Param("username")String username);

    @Update("update email_active set STATUS = #{status} where USERNAME = #{userName}")
    void setStatus(@Param("status")String status,@Param("userName")String userName);

    @Delete("delete from email_active where USERNAME = #{username}")
    void deleteByUsername(@Param("username")String username);

    @Insert("insert into email_active values(#{username},#{email},#{actiCode},#{due_time},#{status}) ")
    void insert(@Param("username")String username,@Param("email") String email,@Param("actiCode")String actiCode,@Param("due_time")String due_time,@Param("status")int status);
}

package com.gis.demo.mapper;

import com.gis.demo.domain.UserInfo;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserInfoDao {

    @Select("select * from userinfo where USERNAME=#{username}")
    UserInfo findUserInfoByUsername(@Param("username") String username);

    @Insert("insert into userinfo values(#{username},#{phone},#{homeAddress},#{others},#{email},#{name},#{workplace})")
    void insertUserinfo(@Param("username") String username,@Param("phone") String phone,@Param("homeAddress") String homeAddress,@Param("others") String others,@Param("email") String email,@Param("name") String name,@Param("workplace") String workplace);

    @Update("update userinfo set PHONE=#{phone},HOMEADDRESS=#{homeAddress},OTHERS=#{others},EMAIL=#{email},NAME=#{name},WORKPLACE=#{workplace}  where USERNAME=#{username}")
    void updateUserinfo(@Param("username") String username,@Param("phone") String phone,@Param("homeAddress") String homeAddress,@Param("others") String others,@Param("email") String email,@Param("name") String name,@Param("workplace") String workplace);

    @Delete("delete from userinfo where USERNAME = #{username}")
    void deleteByUsername(@Param("username")String username);
}

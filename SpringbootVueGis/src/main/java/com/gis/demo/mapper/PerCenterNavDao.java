package com.gis.demo.mapper;

import com.gis.demo.domain.PersonalCenterNav;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PerCenterNavDao {

    @Select("select * from percenternav")
    List<PersonalCenterNav> getPerCenterNavList();

    @Insert("insert into percenternav values(#{icon},#{cnname},#{enname},#{url})")
    void addSiderNav(@Param("icon")String icon,@Param("cnname")String cnname,@Param("enname") String enname,@Param("url")String url);

    @Update("update percenternav set ICON = #{icon},CN_NAME =#{cnname},EN_NAME = #{enname},SRC_URL = #{url} where CN_NAME = #{oldcnname}")
    void updateSiderNav(@Param("icon")String icon,@Param("cnname")String cnname,@Param("enname") String enname,@Param("url")String url,@Param("oldcnname")String oldcnname);

    @Delete("delete from percenternav where CN_NAME = #{oldcnname}")
    void removeSiderNav(@Param("oldcnname")String oldcnname);

    @Select("select * from percenternav")
    List<PersonalCenterNav> findPerCenterNavList();
}

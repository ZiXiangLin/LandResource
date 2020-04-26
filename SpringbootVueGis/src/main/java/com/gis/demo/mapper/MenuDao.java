package com.gis.demo.mapper;

import com.gis.demo.domain.Menu;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MenuDao {

//    @Select("select menu.MENU_ID,dic.CNNAME,dic.ENNAME,menu.MENU_ICON from MENU as menu,TBDATADIC as dic where menu.DIC_ID = dic.ID")
    @Select("select * from menu")
    List<Menu> findMenuList();

    @Insert("insert into menu values(#{menu_id},#{dic_id},#{menu_icon})")
    void insert(@Param("menu_id")String menu_id,@Param("dic_id")int dic_id,@Param("menu_icon")String menu_icon);

    @Update("update menu set MENU_ICON = #{menu_icon} where DIC_ID = #{dicId}")
    void updateMenuIcon(@Param("menu_icon")String menu_icon,@Param("dicId")int dicId);

    @Delete("delete from menu where DIC_ID = #{dicId}")
    void deleteByDicId(@Param("dicId")int dicId);
}

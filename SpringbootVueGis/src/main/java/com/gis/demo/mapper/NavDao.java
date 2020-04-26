package com.gis.demo.mapper;

import java.util.List;


import com.gis.demo.domain.Nav;
import org.apache.ibatis.annotations.*;


@Mapper
public interface NavDao {

    @Select("select * from navigation")
    List<Nav> findNavList();

    @Select("select * from navigation where NAV_LEVEL = #{navLevel}")
    List<Nav> findByNavLevel(@Param("navLevel") int navLevel);

    @Select("select nav.NAV_ID,dic.CNNAME,dic.ENNAME,nav.NAV_ICON,nav.NAV_URL_CN,nav.NAV_URL_EN,nav.NAV_LEVEL,nav.PARENT_ID,nav.ISLEAF from NAVIGATION as nav,TBDATADIC as dic where nav.DIC_ID = dic.ID and nav.PARENT_ID=#{parent_id}")
    List<Nav> findNavListByParentId(@Param("parent_id") String parent_id);

    @Select("select * from navigation where PARENT_ID = #{parent_id}")
    List<Nav> findNavListFormatByParentId(@Param("parent_id") String parent_id);

    @Select("select * from navigation where DIC_ID = #{dicId}")
    Nav findByDicId(@Param("dicId") int dicId);

    @Select("select nav.NAV_ID,nav.DIC_ID,dic.CNNAME,dic.ENNAME,nav.NAV_ICON,nav.NAV_URL_CN,nav.NAV_URL_EN,nav.NAV_LEVEL,nav.PARENT_ID,nav.ISLEAF from NAVIGATION as nav,TBDATADIC as dic where nav.DIC_ID = dic.ID and nav.NAV_LEVEL= #{nav_level}")
    List<Nav> findMenuList(@Param("nav_level") int nav_level);

    @Update("update navigation set ISLEAF= #{i} where NAV_ID = #{nav_id}")
    void updateByNavId(@Param("nav_id") String nav_id,@Param("i") int i);

    @Select("select * from navigation where NAV_ID = #{nav_id}")
    Nav findByNavId(@Param("nav_id") String nav_id);

    @Select("select max(DIC_ID) from navigation")
    int findMaxDicId();

    @Insert("insert into navigation values(#{navId},#{dicId},#{navIcon},#{navUrlEn},#{navUrlCn},#{navLevel},#{parentId},#{isLeaf})")
    void insert(@Param("navId") String navId,@Param("dicId") int dicId,@Param("navIcon") String navIcon,@Param("navUrlEn") String navUrlEn,@Param("navUrlCn") String navUrlCn,@Param("navLevel") int navLevel,@Param("parentId") String parentId,@Param("isLeaf") int isLeaf);

    @Update("update navigation set NAV_ICON = #{navIcon} where DIC_ID = #{dicId}")
    void updateIcon(@Param("navIcon") String navIcon,@Param("dicId") int dicId);

    @Delete("delete from navigation where DIC_ID = #{dicId}")
    void deleteByDicId(@Param("dicId")int dicId);

    @Update("update navigation set NAV_ICON = #{navIcon} where NAV_ID = #{navId}")
    void updateIconByNavId(@Param("navIcon") String navIcon,@Param("navId") String navId);

    @Select("select select nav.DIC_ID,nav.NAV_URL_CN,nav.NAV_LEVEL,nav.ISLEAF,dic.CNNAME,dic.ENNAME from NAVIGATION as nav,TBDATADIC as dic where nav.DIC_ID = dic.ID and dic.CNNAME=#{cnname}")
    Nav findNavInfoByCnname(@Param("cnname") String cnname);

    @Select("select nav.DIC_ID,nav.NAV_URL_EN,nav.NAV_LEVEL,nav.ISLEAF,dic.CNNAME,dic.ENNAME from NAVIGATION as nav,TBDATADIC as dic where nav.DIC_ID = dic.ID and dic.ENNAME=#{enname}")
    Nav findNavInfoByEnname(@Param("enname") String enname);

    @Select("select max(NAV_LEVEL) from NAVIGATION")
    int findMaxLevel();

    //dicDao
    //void deleteNode(String nav_id);

    @Update("update TBDATADIC dic,NAVIGATION nav set dic.CNNAME=#{cnname_changed},dic.ENNAME=#{enname_changed},nav.NAV_URL_CN=#{url_changed} where nav.DIC_ID = dic.ID and nav.NAV_ID= #{nav_id}")
    void updateNode(@Param("nav_id") String nav_id,@Param("cnname_changed") String cnname_changed,@Param("enname_changed") String enname_changed,@Param("url_changed") String url_changed);

    @Select("select nav.NAV_ID,dic.CNNAME,dic.ENNAME,nav.NAV_ICON,nav.NAV_URL_CN,nav.NAV_URL_EN,nav.NAV_LEVEL,nav.PARENT_ID,nav.ISLEAF from NAVIGATION as nav,TBDATADIC as dic where nav.DIC_ID = dic.ID and nav.PARENT_ID like #{parent_id}")
    List<Nav> makeNavId(@Param("parent_id")String parent_id);

    @Select("select * from NAVIGATION where NAV_ID=#{nav_id}")
    Nav findNavInfoByNavId(@Param("nav_id") String nav_id);

    @Select("select * from NAVIGATION where DIC_ID=#{dic_ic}")
    Nav findNavInfoByDicId(@Param("dic_id")int dic_id);

    @Update("update navigation set ISLEAF = #{isleaf} where NAV_ID = #{navId}")
    void setIsLeafByNavId(@Param("navId") String navId,@Param("isleaf")int isleaf);
}

package com.gis.demo.mapper;

import com.gis.demo.domain.Wcs;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface WcsDao {

    @Select("select max(id) from wcs")
    int getId();

    @Insert("insert into wcs values(#{id},#{wcsurl})")
    void insert(@Param("id")int id,@Param("wcsurl")String wcsurl);

    @Select("select * from wcs")
    List<Wcs> findWcsList();
}

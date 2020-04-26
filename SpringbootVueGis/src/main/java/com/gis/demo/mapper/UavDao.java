package com.gis.demo.mapper;

import com.gis.demo.domain.Uav;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UavDao {

    @Select("select max(id) from uav")
    int getId();

    @Insert("insert into uav values(#{id},#{uavImageFileDir},#{latitude},#{longitude})")
    void insert(@Param("id")int id,@Param("uavImageFileDir")String uavImageFileDir,@Param("latitude")double latitude,@Param("longitude")double longitude);

    @Select("select * from uav")
    List<Uav> findUavList();
}

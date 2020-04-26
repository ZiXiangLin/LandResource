package com.gis.demo.mapper;

import com.gis.demo.domain.Service;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ServiceDao {

    @Select("select * from service where TYPE=#{type}")
    List<Service> findByType(@Param("type") String type);

    @Select("select * from service where CNNAME = #{cnname}")
    Service findByCnname(@Param("cnname")String cnname);
}

package com.gis.demo.mapper;

import com.gis.demo.domain.Dic;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DicDao {

    @Select("select * from tbdatadic where ID = #{id}")
    Dic findById(@Param("id") int id);

    @Select("select * from tbdatadic where CNNAME = #{cnname}")
    Dic findByCnname(@Param("cnname") String cnname);

    @Insert("insert into tbdatadic values(#{id},#{category},#{newCnName},#{newEnName})")
    int insert(@Param("id") int id,@Param("category") String category,@Param("newCnName") String newCnName,@Param("newEnName") String newEnName);

    @Update("update tbdatadic set CNNAME =#{cnname} , ENNAME = #{enname} where ID = #{id}")
    void update(@Param("cnname")String cnname,@Param("enname")String enname,@Param("id")int id);

    //deleteNode
    @Delete("delete from tbdatadic where ID = #{dicId}")
    void deleteByDicId(@Param("dicId") int dicId);

    @Select("select * from tbdatadic")
    List<Dic> findDicList();

    @Select("select from tbdatadic where CNNAME LIKE '%\"+#{cnname}+\"%'")
    List<Dic> findByCnnameLike(@Param("cnname")String cnname);
}

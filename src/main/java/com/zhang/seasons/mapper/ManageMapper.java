package com.zhang.seasons.mapper;

import com.zhang.seasons.bean.Manage;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ManageMapper {
    @Insert("insert into manage " +
            "values(#{uid}, #{max}, #{current})")
    int insert(Manage manage);

    @Delete("delete from manage " +
            "where uid = #{uid}")
    int delete(int uid);

    @Update("update manage " +
            "set max = #{max} " +
            "where uid = #{uid}")
    int updateMax(int uid, int max);

    @Update("update manage " +
            "set current = current + #{addition} " +
            "where uid = #{uid}")
    int updateCurrent(int uid, int addition);

    @Select("select max - current " +
            "from manage " +
            "where uid = #{uid}")
    int isMax(int uid);
}

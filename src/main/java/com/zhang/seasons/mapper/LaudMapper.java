package com.zhang.seasons.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

public interface LaudMapper {
    @Insert("insert into laud " +
            "values(#{uid}, #{wid}, #{created})")
    int insert(int uid, int wid, Timestamp created);

    @Delete("delete from laud " +
            "where uid = #{uid} " +
            "and wid = #{wid}")
    int delete(int uid, int wid);

    @Select("select count(*) " +
            "from laud " +
            "where uid = #{uid} " +
            "and wid = #{wid}")
    int select(int uid, int wid);

    @Select("select count(*) " +
            "from laud " +
            "where wid = #{wid}")
    int selectCount(int wid);

    @Select("select wid " +
            "from laud " +
            "where uid = #{uid} " +
            "order by created desc")
    List<Integer> selectList(int uid);
}

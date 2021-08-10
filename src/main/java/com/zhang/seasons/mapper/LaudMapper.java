package com.zhang.seasons.mapper;

import com.zhang.seasons.bean.Laud;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

public interface LaudMapper {
    @Insert("insert into laud " +
            "values(#{uid}, #{wid}, #{created}, #{title}, #{url})")
    int insert(Laud laud);

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

    @Select("select * " +
            "from laud " +
            "where uid = #{uid} " +
            "order by created desc")
    @ResultType(Laud.class)
    List<Laud> selectList(int uid);
}

package com.zhang.seasons.mapper;

import com.zhang.seasons.bean.Style;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

public interface StyleMapper {
    @Insert("insert into style(name, work_num, weekly_num, daily_num, updated) " +
            "values(#{name}, 0, 0, 0, #{updated})")
    int insert(Style style);

    @Delete("delete from style " +
            "where sid = #{sid}")
    int delete(int sid);

    @Update("update style " +
            "set name = #{name} " +
            "where sid = #{sid}")
    int updateName(int sid, String name);

    @Update("update style " +
            "set work_num = work_num + #{addition1}, " +
            "weekly_num = weekly_num + #{addition2}, " +
            "daily_num = daily_num + #{addition3}, " +
            "updated = #{updated}" +
            "where sid = #{sid}")
    int updateWorkNum(int sid, int addition1, int addition2, int addition3, Timestamp updated);

    @Update("update style " +
            "set weekly_num = 0, " +
            "daily_num = 0")
    void eraseTimelyNum();

    @Select("select * " +
            "from style " +
            "where sid = #{sid}")
    @ResultType(Style.class)
    Style select(int sid);

    @Select("select * " +
            "from style " +
            "where name like '%${name}%'")
    @ResultType(Style.class)
    List<Style> searchByName(String name);

    @Select("select * " +
            "from style " +
            "order by work_num desc " +
            "limit #{amount}")
    @ResultType(Style.class)
    List<Style> selectByWorkNum(int amount);

    @Select("select * " +
            "from style " +
            "order by weekly_num desc " +
            "limit #{amount}")
    @ResultType(Style.class)
    List<Style> selectByWeeklyNum(int amount);

    @Select("select * " +
            "from style " +
            "order by daily_num desc " +
            "limit #{amount}")
    @ResultType(Style.class)
    List<Style> selectByDailyNum(int amount);

    @Select("select * " +
            "from style")
    @ResultType(Style.class)
    List<Style> selectAll();
}

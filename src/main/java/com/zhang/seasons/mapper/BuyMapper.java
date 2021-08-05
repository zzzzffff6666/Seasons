package com.zhang.seasons.mapper;

import com.zhang.seasons.bean.Buy;
import javafx.util.Pair;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

public interface BuyMapper {
    @Insert("insert into buy " +
            "values(#{uid}, #{wid}, #{price}, #{type}, #{way}, #{created})")
    int insert(Buy buy);

    @Delete("delete from buy " +
            "where uid = #{uid} " +
            "and wid = #{wid}")
    int delete(int uid, int wid);

    @Select("select * " +
            "from buy " +
            "where uid = #{uid} " +
            "and wid = #{wid}")
    @ResultType(Buy.class)
    Buy select(int uid, int wid);

    @Select("select * " +
            "from buy " +
            "where uid = #{uid} " +
            "order by created desc")
    @ResultType(Buy.class)
    List<Buy> selectByUid(int uid);

    @Select("select * " +
            "from buy " +
            "where wid = #{wid} " +
            "order by created desc")
    @ResultType(Buy.class)
    List<Buy> selectByWid(int wid);

    @Select("select * " +
            "from buy " +
            "where created between #{start} and #{end}")
    @ResultType(Buy.class)
    List<Buy> selectByTime(Timestamp start, Timestamp end);

    @Select("select * " +
            "from buy")
    @ResultType(Buy.class)
    List<Buy> selectAll();

    @Select("select count(*), sum(price) " +
            "from buy " +
            "where wid = #{wid}")
    Pair<Integer, Float> selectSell(int wid);
}

package com.zhang.seasons.mapper;

import com.zhang.seasons.bean.Subscribe;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SubscribeMapper {
    @Insert("insert into subscribe " +
            "values(#{subscriber}, #{publisher}, #{level}, #{created})")
    int insert(Subscribe sub);

    @Delete("delete from subscribe " +
            "where subscriber = #{subscriber} " +
            "and publisher = #{publisher}")
    int delete(int subscriber, int publisher);

    @Update("update subscribe " +
            "set level = #{level} " +
            "where subscriber = #{subscriber} " +
            "and publisher = #{publisher}")
    int update(Subscribe sub);

    @Select("select * " +
            "from subscribe " +
            "where subscriber = #{subscriber} " +
            "and publisher = #{publisher}")
    @ResultType(Subscribe.class)
    Subscribe select(int subscriber, int publisher);

    @Select("select * " +
            "from subscribe " +
            "where publisher = #{publisher} " +
            "order by created desc")
    @ResultType(Subscribe.class)
    List<Subscribe> selectByPublisher(int publisher);

    @Select("select * " +
            "from subscribe " +
            "where subscriber = #{subscriber} " +
            "order by created desc")
    @ResultType(Subscribe.class)
    List<Subscribe> selectBySubscriber(int subscriber);

    @Select("select * " +
            "from subscribe")
    @ResultType(Subscribe.class)
    List<Subscribe> selectAll();
}

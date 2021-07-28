package com.zhang.seasons.mapper;

import com.zhang.seasons.bean.Subscription;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SubscriptionMapper {
    @Insert("insert into subscription " +
            "values(#{subscriber}, #{publisher}, #{level})")
    int insert(Subscription sub);

    @Delete("delete from subscription " +
            "where subscriber = #{subscriber} " +
            "and publisher = #{publisher}")
    int delete(int subscriber, int publisher);

    @Update("update subscription " +
            "set level = #{level} " +
            "where subscriber = #{subscriber} " +
            "and publisher = #{publisher}")
    int update(Subscription sub);

    @Select("select * " +
            "from subscription " +
            "where subscriber = #{subscriber} " +
            "and publisher = #{publisher}")
    @ResultType(Subscription.class)
    Subscription select(int subscriber, int publisher);

    @Select("select * " +
            "from subscription " +
            "where publisher = #{publisher}")
    @ResultType(Subscription.class)
    List<Subscription> selectByPublisher(int publisher);

    @Select("select * " +
            "from subscription " +
            "where subscriber = #{subscriber}")
    @ResultType(Subscription.class)
    List<Subscription> selectBySubscriber(int subscriber);
}

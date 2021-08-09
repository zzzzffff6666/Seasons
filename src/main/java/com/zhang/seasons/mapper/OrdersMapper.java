package com.zhang.seasons.mapper;

import com.zhang.seasons.bean.Orders;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

public interface OrdersMapper {
    @Insert("insert into orders(oid, uid, coin, state, created) " +
            "values(null, #{uid}, #{coin}, 0, #{created})")
    int insert(Orders orders);

    @Delete("delete from orders " +
            "where oid = #{oid}")
    int delete(int oid);

    @Update("update orders " +
            "set state = #{state} " +
            "where oid = #{oid}")
    int updateState(int oid, int state);

    @Select("select * " +
            "from orders " +
            "where oid = #{oid}")
    @ResultType(Orders.class)
    Orders select(int oid);

    @Select("select * " +
            "from orders " +
            "where uid = #{uid} " +
            "order by created desc")
    @ResultType(Orders.class)
    List<Orders> selectByUid(int uid);

    @Select("select * " +
            "from orders " +
            "where uid = #{uid} " +
            "and state = #{state} " +
            "order by created desc")
    @ResultType(Orders.class)
    List<Orders> selectByUidAndState(int uid, int state);

    @Select("select * " +
            "from orders " +
            "where created between #{start} and #{end}")
    @ResultType(Orders.class)
    List<Orders> selectByTime(Timestamp start, Timestamp end);

    @Select("select * " +
            "from orders " +
            "where created between #{start} and #{end} " +
            "and state = #{state}")
    @ResultType(Orders.class)
    List<Orders> selectByTimeAndState(Timestamp start, Timestamp end, int state);

    @Select("select * " +
            "from orders " +
            "where state = #{state} " +
            "order by created desc")
    List<Orders> selectByState(int state);

    @Select("select * " +
            "from orders " +
            "order by created desc")
    @ResultType(Orders.class)
    List<Orders> selectAll();
}

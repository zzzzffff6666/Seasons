package com.zhang.seasons.mapper;

import com.zhang.seasons.bean.User;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

public interface UserMapper {
    @Insert("insert into user(name, phone, password, salt, coin, active, login_time) " +
            "values(#{name}, #{phone}, #{password}, #{salt}, 0, 1, #{loginTime})")
    int insert(User user);

    @Delete("delete from user " +
            "where uid = #{uid}")
    int delete(int uid);

    @Update("update user " +
            "set name = #{name}, " +
            "phone = #{phone} " +
            "where uid = #{uid}")
    int updateInfo(int uid, String name, String phone);

    @Update("update user " +
            "set password = #{password}, " +
            "salt = #{salt} " +
            "where uid = #{uid}")
    int updatePassword(int uid, String password, String salt);

    @Update("update user " +
            "set coin = coin + #{addition} " +
            "where uid = #{uid}")
    int updateCoin(int uid, float addition);

    @Update("update user " +
            "set active = #{active} " +
            "where uid = #{uid}")
    int updateActive(int uid, boolean active);

    @Update("update user " +
            "set login_time = #{loginTime} " +
            "where uid = #{uid}")
    void updateLoginTime(int uid, Timestamp loginTime);

    @Select("select count(*) " +
            "from user " +
            "where name = #{name}")
    int selectName(String name);

    @Select("select coin " +
            "from user " +
            "where uid = #{uid}")
    float selectCoin(int uid);

    @Select("select * " +
            "from user " +
            "where uid = #{uid}")
    @ResultType(User.class)
    User selectByUid(int uid);

    @Select("select * " +
            "from user " +
            "where name = #{name}")
    @ResultType(User.class)
    User selectByName(String name);

    @Select("select * " +
            "from user " +
            "where phone = #{phone}")
    @ResultType(User.class)
    User selectByPhone(String phone);

    @Select("select uid, name, phone, coin, active " +
            "from user " +
            "where active = #{active}")
    @ResultType(User.class)
    List<User> selectByActive(boolean active);

    @Select("select uid, name, phone, coin, active " +
            "from user")
    @ResultType(User.class)
    List<User> selectAll();

    @Select("select uid " +
            "from user " +
            "where login_time between #{start} and #{end}")
    List<Integer> selectUidByLoginTime(Timestamp start, Timestamp end);
}

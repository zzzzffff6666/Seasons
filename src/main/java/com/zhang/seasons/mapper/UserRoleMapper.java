package com.zhang.seasons.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserRoleMapper {
    @Insert("insert into user_role " +
            "values(#{uid}, #{rid})")
    int insert(int uid, int rid);

    @Delete("delete from user_role " +
            "where uid = #{uid} " +
            "and rid = #{rid}")
    int delete(int uid, int rid);

    @Delete("delete from user_role " +
            "where uid = #{uid}")
    int deleteByUid(int uid);

    @Select("select rid " +
            "from user_role " +
            "where uid = #{uid}")
    List<Integer> selectByUid(int uid);
}

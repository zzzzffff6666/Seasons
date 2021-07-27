package com.zhang.seasons.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RolePermissionMapper {
    @Select("select pid " +
            "from role_permission " +
            "where rid = #{rid}")
    List<Integer> selectByRid(int rid);

    @Select("<script> " +
            "select distinct pid " +
            "from role_permission " +
            "where rid in " +
            "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'> " +
            "#{item} " +
            "</foreach> " +
            "</script>")
    List<Integer> selectByList(@Param("list") List<Integer> list);
}

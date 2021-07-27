package com.zhang.seasons.mapper;

import com.zhang.seasons.bean.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RoleMapper {
    @Select("<script> " +
            "select name " +
            "from role " +
            "where rid in " +
            "<foreach item='item' index='index' collection='list' open=')' separator=',' close=')'> " +
            "#{item} " +
            "</foreach> " +
            "</script>")
    List<String> selectNameByList(@Param("list") List<Integer> list);

    @Select("<script> " +
            "select name " +
            "from role " +
            "where rid in " +
            "<foreach item='item' index='index' collection='list' open=')' separator=',' close=')'> " +
            "#{item} " +
            "</foreach> " +
            "</script>")
    @ResultType(Role.class)
    List<Role> selectByList(@Param("list") List<Integer> list);

    @Select("select * " +
            "from role")
    @ResultType(Role.class)
    List<Role> selectAll();
}

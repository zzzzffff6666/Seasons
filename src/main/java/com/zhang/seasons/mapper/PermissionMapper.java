package com.zhang.seasons.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionMapper {
    @Select("<script> " +
            "select name " +
            "from permission " +
            "where pid in " +
            "<foreach item='item' index='index' collection='list' open='(' separator=',' close=')'> " +
            "#{item} " +
            "</foreach> " +
            "</script>")
    List<String> selectByList(@Param("list") List<Integer> list);
}

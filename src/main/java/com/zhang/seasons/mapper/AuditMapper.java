package com.zhang.seasons.mapper;

import com.zhang.seasons.bean.Audit;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

public interface AuditMapper {
    @Insert("insert into audit " +
            "values(#{item}, #{auditor}, #{auditorName}, #{state}, #{opinion}, #{created})")
    int insert(Audit audit);

    @Delete("delete from audit " +
            "where item = #{item} " +
            "and auditor = #{auditor}")
    int delete(String item, int auditor);

    @Select("select * " +
            "from audit " +
            "where auditor = #{auditor} " +
            "order by created desc")
    @ResultType(Audit.class)
    List<Audit> selectByAuditor(int auditor);

    @Select("select * " +
            "from audit " +
            "where created between #{start} and #{end} " +
            "order by created desc")
    @ResultType(Audit.class)
    List<Audit> selectByTime(Timestamp start, Timestamp end);
}

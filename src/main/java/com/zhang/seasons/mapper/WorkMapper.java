package com.zhang.seasons.mapper;

import com.zhang.seasons.bean.Work;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface WorkMapper {
    @Insert("insert into work " +
            "values(null, #{uid}, #{style}, #{title}, #{content}, #{laudNum}" +
            "#{price}, #{priceBiz}, #{url}, #{type}, #{state}, #{created})")
    int insert(Work work);

    @Delete("delete from work " +
            "where wid = #{wid}")
    int delete(int wid);

    @Update("update work " +
            "set sid = #{sid}, " +
            "style = #{style}, " +
            "title = #{title}, " +
            "content = #{content}, " +
            "price = #{price}, " +
            "price_biz = #{priceBiz} " +
            "where wid = #{wid}")
    int update(Work work);

    @Update("update work " +
            "set laud_num = laud_num + #{addition}")
    int updateLaud(int wid, int addition);

    @Update("update work " +
            "set state = #{state} " +
            "where wid = #{wid}")
    int updateState(int wid, int state);

    @Select("select * " +
            "from work " +
            "where wid = #{wid}")
    @ResultType(Work.class)
    Work select(int wid);

    @Select("select * " +
            "from work " +
            "where state = 0 " +
            "and title like '%${title}%' " +
            "order by #{sort}")
    @ResultType(Work.class)
    List<Work> selectByTitle(String title, String sort);

    @Select("select * " +
            "from work " +
            "where state = 0 " +
            "and type = #{type} " +
            "and title like '%${title}%' " +
            "order by #{sort}")
    @ResultType(Work.class)
    List<Work> selectByTitleAndType(String title, String type, String sort);

    @Select("select * " +
            "from work " +
            "where state = 0 " +
            "and style = #{style} " +
            "order by #{sort}")
    @ResultType(Work.class)
    List<Work> selectByStyle(String style, String sort);

    @Select("select * " +
            "from work " +
            "where state = 0 " +
            "and type = #{type} " +
            "and style = #{style} " +
            "order by #{sort}")
    @ResultType(Work.class)
    List<Work> selectByStyleAndType(String style, String type, String sort);

    @Select("select * " +
            "from work " +
            "where state = 0 " +
            "and uid = #{uid} " +
            "order by #{sort}")
    @ResultType(Work.class)
    List<Work> selectByUid(int uid, String sort);

    @Select("select * " +
            "from work " +
            "where state = 0 " +
            "and uid = #{uid} " +
            "and type = #{type} " +
            "order by #{sort}")
    @ResultType(Work.class)
    List<Work> selectByUidAndType(int uid, String type, String sort);

    @Select("select * " +
            "from work " +
            "where state = 0 " +
            "and price between #{down} and #{up} " +
            "order by #{sort}")
    @ResultType(Work.class)
    List<Work> selectByPrice(float up, float down, String sort);

    @Select("select * " +
            "from work " +
            "where state = 0 " +
            "and type = #{type} " +
            "and price between #{down} and #{up} " +
            "order by #{sort}")
    @ResultType(Work.class)
    List<Work> selectByPriceAndType(float up, float down, String type, String sort);

    @Select("select * " +
            "from work " +
            "where state = 0 " +
            "order by #{sort}")
    @ResultType(Work.class)
    List<Work> selectAll(String sort);

    @Select("select * " +
            "from work " +
            "where state = 1 " +
            "order by #{sort}")
    @ResultType(Work.class)
    List<Work> selectByDisapprove(String sort);

    @Select("select * " +
            "from work " +
            "where state = 1 " +
            "and type = #{type} " +
            "order by #{sort}")
    @ResultType(Work.class)
    List<Work> selectByDisapproveAndType(String type, String sort);

    @Select("select * " +
            "from work " +
            "order by #{sort}")
    @ResultType(Work.class)
    List<Work> selectAllByAdmin(String sort);
}

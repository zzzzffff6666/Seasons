package com.zhang.seasons.mapper;

import com.zhang.seasons.bean.Work;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface WorkMapper {
    @Insert("insert into work " +
            "values(null, #{uid}, #{sid}, #{style}, #{title}, #{content}, " +
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
            "set state = #{state} " +
            "where wid = #{wid}")
    int updateState(int wid, int state);

    @ResultType(Work.class)
    Work select(int wid);
    @ResultType(Work.class)
    List<Work> selectByTitle(String title);
    @ResultType(Work.class)
    List<Work> selectByTitleAndType(String title, String type);
    @ResultType(Work.class)
    List<Work> selectByStyle(String style);
    @ResultType(Work.class)
    List<Work> selectByStyleAndType(String style, String type);
    @ResultType(Work.class)
    List<Work> selectByUid(int uid);
    @ResultType(Work.class)
    List<Work> selectByUidAndType(int uid, String type);
    @ResultType(Work.class)
    List<Work> selectByState(int state);
    @ResultType(Work.class)
    List<Work> selectByStateAndType(int state, String type);
    @ResultType(Work.class)
    List<Work> selectByPrice(float up, float down, String sort);
    @ResultType(Work.class)
    List<Work> selectByPriceAndType(float up, float down, String sort, String type);
}

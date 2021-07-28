package com.zhang.seasons.mapper;

import com.zhang.seasons.bean.Buy;

import java.sql.Timestamp;
import java.util.List;

public interface BuyMapper {
    int insert(Buy buy);
    int delete(int uid, int wid);
    Buy select(int uid, int wid);
    List<Buy> selectByUid(int uid);
    List<Buy> selectByWid(int wid);
    List<Buy> selectByDate(Timestamp start, Timestamp end);
}

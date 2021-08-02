package com.zhang.seasons.service;

import com.zhang.seasons.bean.Style;
import com.zhang.seasons.mapper.StyleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class StyleService {
    private static final int STYLE_RECOMMEND_AMOUNT = 6;

    @Autowired
    private StyleMapper styleMapper;

    public boolean insertStyle(Style style) {
        return styleMapper.insert(style) == 0;
    }

    public boolean deleteStyle(int sid) {
        return styleMapper.delete(sid) == 1;
    }

    public boolean updateStyleName(int sid, String name) {
        return styleMapper.updateName(sid, name) == 1;
    }

    public boolean updateStyleWorkNum(int sid, int[] addition, Timestamp updated) {
        return styleMapper.updateWorkNum(sid, addition[0], addition[1], addition[2], updated) == 1;
    }

    public void eraseStyleTimelyNum() {
        styleMapper.eraseTimelyNum();
    }

    public Style selectStyle(int sid) {
        return styleMapper.select(sid);
    }

    public List<Style> searchStyleByName(String name) {
        return styleMapper.searchByName(name);
    }

    public List<Style> selectStyleByWorkNum() {
        return styleMapper.selectByWorkNum(STYLE_RECOMMEND_AMOUNT);
    }

    public List<Style> selectStyleByWeeklyNum() {
        return styleMapper.selectByWeeklyNum(STYLE_RECOMMEND_AMOUNT);
    }

    public List<Style> selectStyleByDailyNum() {
        return styleMapper.selectByDailyNum(STYLE_RECOMMEND_AMOUNT);
    }

    public List<Style> selectAllStyle() {
        return styleMapper.selectAll();
    }
}

package com.zhang.seasons.service;

import com.zhang.seasons.bean.Manage;
import com.zhang.seasons.bean.Style;
import com.zhang.seasons.mapper.ManageMapper;
import com.zhang.seasons.mapper.StyleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class StyleService {
    @Autowired
    private StyleMapper styleMapper;
    @Autowired
    private ManageMapper manageMapper;

    // Style 部分

    @Transactional
    public boolean insertStyle(Style style) {
        return styleMapper.insert(style) == 1 && manageMapper.updateCurrent(style.getManager(), 1) == 1;
    }

    @Transactional
    public boolean deleteStyle(int sid, int uid) {
        return styleMapper.delete(sid) == 1 && manageMapper.updateCurrent(uid, -1) == 1;
    }

    @Transactional
    public boolean updateStyleManager(int sid, int manager, int old) {
        boolean suc = styleMapper.updateManager(sid, manager) == 1;
        suc &= manageMapper.updateCurrent(old, -1) == 1 && manageMapper.updateCurrent(manager, 1) == 1;
        return suc;
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

    public int selectStyleManager(int sid) {
        return styleMapper.selectManager(sid);
    }

    public Style selectStyle(int sid) {
        return styleMapper.select(sid);
    }

    public List<Style> searchStyleByName(String name) {
        return styleMapper.searchByName(name);
    }

    public List<Style> selectStyleByManager(int manager) {
        return styleMapper.selectByManager(manager);
    }

    public List<Style> selectStyleByWorkNum(int amount) {
        return styleMapper.selectByWorkNum(amount);
    }

    public List<Style> selectStyleByWeeklyNum(int amount) {
        return styleMapper.selectByWeeklyNum(amount);
    }

    public List<Style> selectStyleByDailyNum(int amount) {
        return styleMapper.selectByDailyNum(amount);
    }

    public List<Style> selectAllStyle() {
        return styleMapper.selectAll();
    }

    // Manage 部分

    public boolean insertManage(Manage manage) {
        return manageMapper.insert(manage) == 1;
    }

    public boolean deleteManage(int uid) {
        return manageMapper.delete(uid) == 1;
    }

    public boolean updateManageMax(int uid, int max) {
        return manageMapper.updateMax(uid, max) == 1;
    }

    public boolean updateManageCurrent(int uid, int addition) {
        return manageMapper.updateCurrent(uid, addition) == 1;
    }

    public boolean isManageMax(int uid) {
        return manageMapper.isMax(uid) == 0;
    }
}

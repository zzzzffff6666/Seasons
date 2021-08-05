package com.zhang.seasons.service;

import com.zhang.seasons.bean.Buy;
import com.zhang.seasons.mapper.BuyMapper;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuyService {
    @Autowired
    private BuyMapper buyMapper;

    public boolean insertBuy(Buy buy) {
        return buyMapper.insert(buy) == 1;
    }

    public boolean deleteBuy(int uid, int wid) {
        return buyMapper.delete(uid, wid) == 1;
    }

    public Buy selectBuy(int uid, int wid) {
        return buyMapper.select(uid, wid);
    }

    public List<Buy> selectBuyByUid(int uid) {
        return buyMapper.selectByUid(uid);
    }

    public List<Buy> selectBuyByWid(int wid) {
        return buyMapper.selectByWid(wid);
    }

    public List<Buy> selectBuyByTime(Timestamp start, Timestamp end) {
        return buyMapper.selectByTime(start, end);
    }

    public List<Buy> selectAllBuy() {
        return buyMapper.selectAll();
    }

    public Map<String, Object> selectWorkSell(int wid) {
        Pair<Integer, Float> sell = buyMapper.selectSell(wid);
        Map<String, Object> result = new HashMap<>();
        result.put("amount", sell.getKey());
        result.put("total", sell.getValue());
        return result;
    }
}

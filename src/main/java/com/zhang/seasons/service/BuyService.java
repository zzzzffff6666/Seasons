package com.zhang.seasons.service;

import com.zhang.seasons.bean.Buy;
import com.zhang.seasons.mapper.BuyMapper;
import com.zhang.seasons.mapper.UserMapper;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuyService {
    @Autowired
    private BuyMapper buyMapper;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public boolean insertBuy(Buy buy, int creator) {
        boolean suc = buyMapper.insert(buy) == 1;
        suc &= userMapper.updateCoin(buy.getUid(), 0 - buy.getPrice()) == 1;
        suc &= userMapper.updateCoin(creator, buy.getPrice() * 0.9f) == 1;
        return suc;
    }

    @Transactional
    public boolean deleteBuy(Buy buy, int creator) {
        boolean suc = buyMapper.delete(buy.getUid(), buy.getWid()) == 1;
        suc &= userMapper.updateCoin(buy.getUid(), buy.getPrice() * 0.9f) == 1;
        suc &= userMapper.updateCoin(creator, 0 - (buy.getPrice() * 0.9f)) == 1;
        return suc;
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

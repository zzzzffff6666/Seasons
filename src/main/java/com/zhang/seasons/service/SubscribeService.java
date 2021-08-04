package com.zhang.seasons.service;

import com.zhang.seasons.bean.Subscribe;
import com.zhang.seasons.mapper.SubscribeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscribeService {
    @Autowired
    private SubscribeMapper subMapper;

    public boolean insertSubscribe(Subscribe sub) {
        return subMapper.insert(sub) == 1;
    }

    public boolean deleteSubscribe(int subscriber, int publisher) {
        return subMapper.delete(subscriber, publisher) == 1;
    }

    public boolean updateSubscribeLevel(Subscribe sub) {
        return subMapper.update(sub) == 1;
    }

    public Subscribe selectSubscribe(int subscriber, int publisher) {
        return subMapper.select(subscriber, publisher);
    }

    public List<Subscribe> selectSubscribeByPublisher(int publisher) {
        return subMapper.selectByPublisher(publisher);
    }

    public List<Subscribe> selectSubscribeBySubscriber(int subscriber) {
        return subMapper.selectBySubscriber(subscriber);
    }

    public List<Subscribe> selectAllSubscribe() {
        return subMapper.selectAll();
    }
}

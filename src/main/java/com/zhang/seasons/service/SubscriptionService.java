package com.zhang.seasons.service;

import com.zhang.seasons.bean.Subscription;
import com.zhang.seasons.mapper.SubscriptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionMapper subMapper;

    public boolean insertSubscription(Subscription sub) {
        return subMapper.insert(sub) == 1;
    }

    public boolean deleteSubscription(int subscriber, int publisher) {
        return subMapper.delete(subscriber, publisher) == 1;
    }

    public boolean updateSubscriptionLevel(Subscription sub) {
        return subMapper.update(sub) == 1;
    }

    public Subscription selectSubscription(int subscriber, int publisher) {
        return subMapper.select(subscriber, publisher);
    }

    public List<Subscription> selectSubscriptionByPublisher(int publisher) {
        return subMapper.selectByPublisher(publisher);
    }

    public List<Subscription> selectSubscriptionBySubscriber(int subscriber) {
        return subMapper.selectBySubscriber(subscriber);
    }

    public List<Subscription> selectAllSubscription() {
        return subMapper.selectAll();
    }
}

package com.zhang.seasons.service;

import com.zhang.seasons.bean.Message;
import com.zhang.seasons.mapper.MessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    public boolean insertMessage(Message message) {
        return messageMapper.insert(message) == 1;
    }

    public boolean insertMessageByList(List<Integer> receivers, int sender, String senderName, String msg, String url, Timestamp now) {
        if (receivers.isEmpty()) return false;
        return messageMapper.insertByList(receivers, sender, senderName, msg, url, now) > 0;
    }
}

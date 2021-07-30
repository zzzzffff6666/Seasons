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

    public boolean insertMessageByList(List<Integer> receivers, Message message) {
        if (receivers.isEmpty()) return false;
        return messageMapper.insertByList(receivers, message.getSender(), message.getSenderName(),
                message.getMsg(), message.getUrl(), message.getCreated()) > 0;
    }

    public boolean deleteMessage(int receiver, int sender, Timestamp created) {
        return messageMapper.delete(receiver, sender, created) == 1;
    }

    public boolean updateMessageRead(Message message) {
        return messageMapper.updateRead(message) == 1;
    }

    public boolean updateMessageReadAll(int receiver) {
        return messageMapper.updateReadAllByReceiver(receiver) > 0;
    }

    public int selectUnreadMessageAmount(int receiver) {
        return messageMapper.selectUnReadMount(receiver);
    }

    List<Message> selectMessageByReceiver(int receiver) {
        return messageMapper.selectByReceiver(receiver);
    }

    List<Message> selectMessageBySender(int sender) {
        return messageMapper.selectBySender(sender);
    }

    List<Message> selectAllMessage() {
        return messageMapper.selectAll();
    }
}

package com.zhang.seasons.controller;

import com.zhang.seasons.http.Result;
import com.zhang.seasons.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

//    public Result insertMessage() {
//
//    }
//
//    public Result insertMessageByList() {
//
//    }
//
//    public Result deleteMessage() {
//
//    }
//
//    public Result updateMessageRead() {
//
//    }
//
//    public Result updateAllMessageRead() {
//
//    }
//
//    public Result selectUnreadMessageAmount() {
//
//    }
//
//    public Result selectMessageReceiver() {
//
//    }
//
//    public Result selectMessage() {
//
//    }
//
//    public Result selectMessageByReceiver() {
//
//    }
//
//    public Result selectMessageBySender() {
//
//    }
//
//    public Result selectAllMessage() {
//
//    }
}

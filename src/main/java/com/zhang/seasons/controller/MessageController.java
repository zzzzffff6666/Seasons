package com.zhang.seasons.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhang.seasons.bean.Message;
import com.zhang.seasons.http.APIMsg;
import com.zhang.seasons.http.Result;
import com.zhang.seasons.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@RestController
public class MessageController {
    private static final int MESSAGE_PAGE_AMOUNT = 20;

    @Autowired
    private MessageService messageService;

    @PostMapping("/message")
    public Result insertMessage(@RequestParam Message message, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        String name = (String) session.getAttribute("name");
        message.setSender(uid);
        message.setSenderName(name);
        message.setCreated(new Timestamp(System.currentTimeMillis()));
        boolean suc = messageService.insertMessage(message);
        return suc ? Result.success() : Result.error(APIMsg.INSERT_ERROR);
    }

    @PostMapping("/message/list")
    public Result insertMessageByList(@RequestParam("receivers") List<Integer> receivers, @RequestParam("msg") String msg,
                                      @RequestParam("url") String url, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        String name = (String) session.getAttribute("name");
        Message message = new Message();
        message.setSender(uid);
        message.setSenderName(name);
        message.setMsg(msg);
        message.setUrl(url);
        message.setCreated(new Timestamp(System.currentTimeMillis()));
        boolean suc = messageService.insertMessageByList(receivers, message);
        return suc ? Result.success() : Result.error(APIMsg.INSERT_ERROR);
    }

    @DeleteMapping("/message")
    public Result deleteMessage(@RequestParam("mid") long mid, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        Message message = messageService.selectMessage(mid);
        if (uid != message.getReceiver() && uid != message.getSender()) return Result.error(APIMsg.AUTH_ERROR);
        boolean suc = messageService.deleteMessage(mid);
        return suc ? Result.success() : Result.error(APIMsg.DELETE_ERROR);
    }

    @PutMapping("/message/read")
    public Result updateMessageRead(@RequestParam("mid") long mid, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        Message message = messageService.selectMessage(mid);
        if (uid != message.getReceiver()) return Result.error(APIMsg.AUTH_ERROR);
        boolean suc = messageService.updateMessageRead(mid);
        return suc ? Result.success() : Result.error(APIMsg.UPDATE_ERROR);
    }

    @PutMapping("/message/read/all")
    public Result updateAllMessageRead(HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        boolean suc = messageService.updateAllMessageRead(uid);
        return suc ? Result.success() : Result.error(APIMsg.UPDATE_ERROR);
    }

    @GetMapping("/message/unread")
    public Result selectUnreadMessageAmount(HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        return Result.success(messageService.selectUnreadMessageAmount(uid));
    }

    @GetMapping("/message/mid/{mid}")
    public Result selectMessage(@PathVariable("mid") long mid, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        Message message = messageService.selectMessage(mid);
        if (uid != message.getReceiver() && uid != message.getSender()) return Result.error(APIMsg.AUTH_ERROR);
        return Result.success(message);
    }

    @GetMapping({"/message/list/receiver", "/message/list/receiver/{page}"})
    public Result selectMessageByReceiver(@PathVariable(value = "page", required = false) Integer page, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        if (page == null) page = 1;
        PageHelper.startPage(page, MESSAGE_PAGE_AMOUNT);
        PageInfo<Message> list = new PageInfo<>(messageService.selectMessageByReceiver(uid));
        return Result.success(list);
    }

    @GetMapping({"/message/list/sender", "/message/list/sender/{page}"})
    public Result selectMessageBySender(@PathVariable(value = "page", required = false) Integer page, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        if (page == null) page = 1;
        PageHelper.startPage(page, MESSAGE_PAGE_AMOUNT);
        PageInfo<Message> list = new PageInfo<>(messageService.selectMessageBySender(uid));
        return Result.success(list);
    }

    @GetMapping({"/message/all", "/message/all/{page}"})
    public Result selectAllMessage(@PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, MESSAGE_PAGE_AMOUNT);
        PageInfo<Message> list = new PageInfo<>(messageService.selectAllMessage());
        return Result.success(list);
    }
}

package com.zhang.seasons.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhang.seasons.bean.Orders;
import com.zhang.seasons.http.APIMsg;
import com.zhang.seasons.http.Result;
import com.zhang.seasons.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

@Slf4j
@RestController
public class OrdersController {
    private static final int ORDERS_PAGE_AMOUNT = 30;

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/orders")
    public Result insertOrders(@RequestParam("coin") float coin, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        Orders orders = new Orders();
        orders.setUid(uid);
        orders.setCoin(coin);
        orders.setCreated(new Timestamp(System.currentTimeMillis()));
        boolean suc = ordersService.insertOrders(orders);
        return suc ? Result.success() : Result.error(APIMsg.INSERT_ERROR);
    }

    @DeleteMapping("/orders")
    public Result deleteOrders(@RequestParam("oid") int oid, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        Orders orders = ordersService.selectOrders(oid);
        if (uid != orders.getUid()) return Result.error(APIMsg.AUTH_ERROR);
        boolean suc = ordersService.deleteOrders(oid);
        return suc ? Result.success() : Result.error(APIMsg.DELETE_ERROR);
    }

    @PutMapping("/orders/state")
    @RequiresPermissions("order:*")
    public Result updateOrderState(@RequestParam("oid") int oid, @RequestParam("state") int state) {
        boolean suc = ordersService.updateOrdersState(oid, state);
        return suc ? Result.success() : Result.error(APIMsg.UPDATE_ERROR);
    }

    @GetMapping("/orders/oid/{oid}")
    public Result selectOrders(@PathVariable("oid") int oid, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        Orders orders = ordersService.selectOrders(oid);
        return uid == orders.getUid() ? Result.success() : Result.error(APIMsg.AUTH_ERROR);
    }

    @GetMapping("/orders-admin/oid/{oid}")
    public Result selectOrdersAsAdmin(@PathVariable("oid") int oid) {
        return Result.success(ordersService.selectOrders(oid));
    }

    @GetMapping({"/orders/list/uid", "/orders/list/uid/{page}"})
    public Result selectOrdersByUid(@PathVariable(value = "page", required = false) Integer page, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        if (page == null) page = 1;
        PageHelper.startPage(page, ORDERS_PAGE_AMOUNT);
        PageInfo<Orders> list = new PageInfo<>(ordersService.selectOrdersByUid(uid));
        return Result.success(list);
    }

    @GetMapping({"/orders-admin/list/uid/{uid}", "/orders-admin/list/uid/{uid}/{page}"})
    public Result selectOrdersByUidAsAdmin(@PathVariable("uid") int uid, @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, ORDERS_PAGE_AMOUNT);
        PageInfo<Orders> list = new PageInfo<>(ordersService.selectOrdersByUid(uid));
        return Result.success(list);
    }

    @GetMapping({"/orders/list/uid_state", "/orders/list/uid_state/{page}"})
    public Result selectOrdersByUidAndState(@RequestParam("state") int state,
                                            @PathVariable(value = "page", required = false) Integer page, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        if (page == null) page = 1;
        PageHelper.startPage(page, ORDERS_PAGE_AMOUNT);
        PageInfo<Orders> list = new PageInfo<>(ordersService.selectOrdersByUidAndState(uid, state));
        return Result.success(list);
    }

    @GetMapping({"/orders-admin/list/uid_state", "/orders-admin/list/uid_state/{page}"})
    public Result selectOrdersByUidAndStateAsAdmin(@RequestParam("state") int state, @RequestParam("uid") int uid,
                                            @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, ORDERS_PAGE_AMOUNT);
        PageInfo<Orders> list = new PageInfo<>(ordersService.selectOrdersByUidAndState(uid, state));
        return Result.success(list);
    }

    @GetMapping({"/orders/list/time", "/orders/list/time/{page}"})
    @RequiresPermissions("orders:*")
    public Result selectOrdersByTime(@RequestParam("start") Timestamp start, @RequestParam("end") Timestamp end,
                                     @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, ORDERS_PAGE_AMOUNT);
        PageInfo<Orders> list = new PageInfo<>(ordersService.selectOrdersByTime(start, end));
        return Result.success(list);
    }

    @GetMapping({"/orders/list/time_state", "/orders/list/time_state/{page}"})
    @RequiresPermissions("orders:*")
    public Result selectOrdersByTimeAndState(@RequestParam("start") Timestamp start, @RequestParam("end") Timestamp end,
                                             @RequestParam("state") int state, @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, ORDERS_PAGE_AMOUNT);
        PageInfo<Orders> list = new PageInfo<>(ordersService.selectOrdersByTimeAndState(start, end, state));
        return Result.success(list);
    }

    @GetMapping({"/orders/list/state", "/orders/list/state/{page}"})
    public Result selectOrdersByState(@RequestParam("state") int state,
                                      @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, ORDERS_PAGE_AMOUNT);
        PageInfo<Orders> list = new PageInfo<>(ordersService.selectOrdersByState(state));
        return Result.success(list);
    }

    @GetMapping({"/orders/all", "/orders/all/{page}"})
    public Result selectAllOrders(@PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, ORDERS_PAGE_AMOUNT);
        PageInfo<Orders> list = new PageInfo<>(ordersService.selectAllOrders());
        return Result.success(list);
    }
}

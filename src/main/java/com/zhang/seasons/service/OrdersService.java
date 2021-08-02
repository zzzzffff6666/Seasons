package com.zhang.seasons.service;

import com.zhang.seasons.bean.Orders;
import com.zhang.seasons.mapper.OrdersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class OrdersService {
    @Autowired
    private OrdersMapper ordersMapper;

    public boolean insertOrders(Orders orders) {
        return ordersMapper.insert(orders) == 1;
    }

    public boolean deleteOrders(int oid) {
        return ordersMapper.delete(oid) == 1;
    }

    public boolean updateOrdersState(int oid, int state) {
        return ordersMapper.updateState(oid, state) == 1;
    }

    public Orders selectOrders(int oid) {
        return ordersMapper.select(oid);
    }

    public List<Orders> selectOrdersByUid(int uid) {
        return ordersMapper.selectByUid(uid);
    }

    public List<Orders> selectOrdersByUidAndState(int uid, int state) {
        return ordersMapper.selectByUidAndState(uid, state);
    }

    public List<Orders> selectOrdersByTime(Timestamp start, Timestamp end) {
        return ordersMapper.selectByTime(start, end);
    }

    public List<Orders> selectOrdersByTimeAndState(Timestamp start, Timestamp end, int state) {
        return ordersMapper.selectByTimeAndState(start, end, state);
    }

    public List<Orders> selectAllOrders() {
        return ordersMapper.selectAll();
    }
}

package com.zhang.seasons.controller;

import com.zhang.seasons.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
}

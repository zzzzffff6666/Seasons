package com.zhang.seasons.controller;

import com.zhang.seasons.service.BuyService;
import com.zhang.seasons.service.StyleService;
import com.zhang.seasons.service.WorkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class WorkController {
    @Autowired
    private StyleService styleService;
    @Autowired
    private WorkService workService;
    @Autowired
    private BuyService buyService;
}

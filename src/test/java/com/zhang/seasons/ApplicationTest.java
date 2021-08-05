package com.zhang.seasons;

import com.zhang.seasons.mapper.BuyMapper;
import com.zhang.seasons.service.BuyService;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class ApplicationTest {
    @Autowired
    BuyMapper buyMapper;
    @Autowired
    BuyService buyService;

    @Test
    void test1() {
        Map<String, Object> sell = buyService.selectWorkSell(1);
        System.out.println(sell.toString());
    }
}

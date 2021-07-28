package com.zhang.seasons.service;

import com.zhang.seasons.mapper.WorkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkService {
    private static final String[] ORDER_SEQ = new String[] {"created", "price", "laud_num"};
    private static final String[] ORDER_TYPE = new String[] {" asc", "desc"};

    @Autowired
    private WorkMapper workMapper;
}

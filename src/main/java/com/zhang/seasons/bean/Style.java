package com.zhang.seasons.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Style {
    private int sid;
    private int manager;
    private String name;
    private int workNum;
    private int weeklyNum;
    private int dailyNum;
    private Timestamp updated;
}

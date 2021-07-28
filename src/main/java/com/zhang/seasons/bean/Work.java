package com.zhang.seasons.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Work {
    private int wid;
    private int uid;
    private int sid;
    private String style;
    private String title;
    private String content;
    private float price;
    private float priceBiz;
    private String url;
    private String type;
    private int state;
    private Timestamp created;
}

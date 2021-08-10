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
    private int creator;
    private String creatorName;
    private String style;
    private String title;
    private String content;
    private int laudNum;
    private float price;
    private float priceBiz;
    private String url;
    private int state;
    private Timestamp created;
    private boolean isLaud;
}

package com.zhang.seasons.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Work {
    private int wid;
    private int uid;
    private String title;
    private String style;
    private String content;
    private float price;
    private float priceBiz;
    private String url;
    private String type;
    private int state;
}

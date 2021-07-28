package com.zhang.seasons.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Buy {
    private int uid;
    private int wid;
    private float price;
    private int type;
    private String way;
    private Timestamp created;
}

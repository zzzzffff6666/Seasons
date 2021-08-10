package com.zhang.seasons.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Laud {
    private int uid;
    private int wid;
    private Timestamp created;
    private String title;
    private String url;
}

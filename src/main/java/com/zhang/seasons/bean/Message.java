package com.zhang.seasons.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private int receiver;
    private int sender;
    private String msg;
    private String url;
    private boolean read;
    private Timestamp created;
}

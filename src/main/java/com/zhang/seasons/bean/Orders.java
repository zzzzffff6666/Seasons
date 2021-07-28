package com.zhang.seasons.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    private int oid;
    private int uid;
    private float coin;
    private int state;
    private Timestamp created;
}

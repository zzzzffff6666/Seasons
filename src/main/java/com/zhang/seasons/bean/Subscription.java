package com.zhang.seasons.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    private int subscriber;
    private int publisher;
    private int level;
}

package com.zhang.seasons.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Audit {
    private String item;
    private int auditor;
    private String auditorName;
    private int state;
    private String opinion;
    private Timestamp created;
}

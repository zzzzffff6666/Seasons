package com.zhang.seasons.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int uid;
    private String name;
    private String phone;
    private String password;
    private String salt;
    private float coin;
    private boolean active;

    public void erasePassword() {
        this.password = null;
        this.salt = null;
    }
}

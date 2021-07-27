package com.zhang.seasons;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.zhang.seasons.mapper")
@SpringBootApplication
public class SeasonsApplication {

    public static void main(String[] args) {

        SpringApplication.run(SeasonsApplication.class, args);

    }
}

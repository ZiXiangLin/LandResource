package com.gis.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import  org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.gis.demo.mapper")
public class SpringBootVueGisApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootVueGisApplication.class, args);
    }
}

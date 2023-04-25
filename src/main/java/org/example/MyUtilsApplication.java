package org.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@MapperScan("org.example.mapper")
@SpringBootApplication
public class MyUtilsApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyUtilsApplication.class, args);
    }


}
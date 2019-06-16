package com.miner.disco.front;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
@tk.mybatis.spring.annotation.MapperScan("com.miner.disco.front.dao")
@MapperScan(basePackages = "com.miner.disco.front.dao")
@ComponentScan(basePackages = {"com.miner.disco.front", "com.miner.disco.boot.support",
        "com.miner.disco.oss.support", "com.miner.disco.mybatis.support"})
public class DiscoFrontApplication {

    public static void main(String[] args) {

        SpringApplication.run(DiscoFrontApplication.class, args);
    }

}


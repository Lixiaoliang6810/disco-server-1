package com.miner.disco.mch;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.miner.disco.mch.dao")
@ComponentScan(basePackages = {"com.miner.disco.mch", "com.miner.disco.boot.support",
        "com.miner.disco.oss.support", "com.miner.disco.mybatis.support"})
public class DiscoMchApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoMchApplication.class, args);
    }

}


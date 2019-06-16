package com.miner.disco.admin;

import com.google.common.collect.Lists;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.miner.disco.admin.dao")
@ComponentScan(basePackages = {"com.miner.disco.admin", "com.miner.disco.boot.support",
        "com.miner.disco.oss.support", "com.miner.disco.mybatis.support"})
public class DiscoAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoAdminApplication.class, args);
    }

}


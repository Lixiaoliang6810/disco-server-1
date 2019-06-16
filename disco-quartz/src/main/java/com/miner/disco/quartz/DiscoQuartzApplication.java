package com.miner.disco.quartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@EnableScheduling
@EnableCaching
@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.miner.disco.quartz.dao")
@ComponentScan(basePackages={"com.miner.*"})
public class DiscoQuartzApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscoQuartzApplication.class, args);
    }

}


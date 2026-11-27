package com.seckill.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: 犬小哈
 * @Date: 2026/3/31 21:40
 * @Version: v1.0.0
 * @Description: 秒杀系统启动类
 **/
@SpringBootApplication
@ComponentScan({"com.seckill.*"}) // 多模块项目中，必需手动指定扫描 com.seckill 包下面的所有类
@MapperScan("com.seckill.common.domain.mapper")
@EnableRetry // 开启 Spring Retry 重试功能
@EnableScheduling // 开启 Spring 定时任务功能
public class SeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class, args);
    }
}

package com.luyuze.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.luyuze.dao"})
public class MyBatisConfig {
}

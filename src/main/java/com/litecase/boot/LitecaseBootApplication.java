package com.litecase.boot;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
//@MapperScan(basePackages = "com.litecase.boot.web.mapper")
@MapperScan(annotationClass = Mapper.class)
// @EnableScheduling 表明该类中存在定时任务
public class LitecaseBootApplication {

    private static final Logger log = LoggerFactory.getLogger(LitecaseBootApplication.class);


    public static void main(String[] args) {


//        Pet pet = new Pet();

        SpringApplication.run(LitecaseBootApplication.class, args);
    }

//    在定时执行的方法上面添加@Scheduled(cron = "*/6 * * * * ?")
//    @Scheduled(cron = "*/6 * * * * ?")
//    public void sayHello() {
//        System.out.println("Hello!");
//    }

}

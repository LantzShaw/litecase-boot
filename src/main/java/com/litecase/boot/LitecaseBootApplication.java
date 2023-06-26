package com.litecase.boot;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;

@SpringBootApplication
//@MapperScan(basePackages = "com.litecase.boot.web.mapper")
@MapperScan(annotationClass = Mapper.class)
public class LitecaseBootApplication {

    private static final Logger log = LoggerFactory.getLogger(LitecaseBootApplication.class);


    public static void main(String[] args) {


//        Pet pet = new Pet();

        SpringApplication.run(LitecaseBootApplication.class, args);
    }

}

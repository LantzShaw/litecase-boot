package com.litecase.boot.web.bean;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class Pet {
    String name;
    int genderType;
    int age;

//    public Pet() {
//        this.name = name;
//        this.genderType = genderType;
//        this.age = age;
//    }

    @Bean("cat")
    public void cat() {

    }
}

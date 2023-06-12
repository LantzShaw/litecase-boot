package com.litecase.boot;

import com.litecase.boot.bean.Pet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class LitecaseBootApplication {

    public static void main(String[] args) {
//        Pet pet = new Pet();

        SpringApplication.run(LitecaseBootApplication.class, args);
    }

}

package com.litecase.boot.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fruits")
public class fruitController {
    @GetMapping("/list")
    String getFruitList() {
        return "apple";
    }
}

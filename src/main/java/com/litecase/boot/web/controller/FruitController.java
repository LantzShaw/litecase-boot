package com.litecase.boot.web.controller;

import com.litecase.boot.web.service.impl.FruitServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fruits")
public class FruitController {
    @Autowired // FruitServiceImpl 需要添加@Service注解，不然会报错
//    @Resource
    FruitServiceImpl fruitServiceImpl;

    @GetMapping("/list")
    String getFruitList() {

//        fruitServiceImpl.list();

//        fruitServiceImpl.list();

        return "apple";
    }
}

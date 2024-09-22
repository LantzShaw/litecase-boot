package com.litecase.boot.web.controller;

import com.litecase.boot.web.model.entity.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/product")
public class ProductController {
    @GetMapping("/list")
    public List<Product> getProductList() {
        // TODO: 获取产品列表
        return null;
    }
}

package com.litecase.boot.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.litecase.boot.web.mapper.ProductMapper;
import com.litecase.boot.web.model.entity.Product;
import com.litecase.boot.web.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
}

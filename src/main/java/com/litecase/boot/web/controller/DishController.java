package com.litecase.boot.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.litecase.boot.web.common.R;
import com.litecase.boot.web.model.dto.DishDto;
import com.litecase.boot.web.model.entity.Category;
import com.litecase.boot.web.model.entity.Dish;
import com.litecase.boot.web.service.CategoryService;
import com.litecase.boot.web.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {


    @Autowired
    DishService dishService;

    @Autowired
    CategoryService categoryService;

    @GetMapping("/page")
    public R<Page<DishDto>> page(int pageIndex, int pageSize, String name) {

        Page<Dish> pageInfo = new Page<>(pageIndex, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Dish::getName, name);

        lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);

        dishService.page(pageInfo, lambdaQueryWrapper);

        // 对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();

        List<DishDto> dishList = records.stream().map(item -> {

            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();

          Category category = categoryService.getById(categoryId);

          if(category != null) {
              String categoryName = category.getName();

              dishDto.setCategoryName(categoryName);
          }

            return dishDto;
        }).collect(Collectors.toList());



        dishDtoPage.setRecords(dishList);


        return R.success(dishDtoPage);
    }
}

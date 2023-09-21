package com.litecase.boot.web.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.litecase.boot.web.exception.CustomException;
import com.litecase.boot.web.mapper.CategoryMapper;
import com.litecase.boot.web.model.entity.Category;
import com.litecase.boot.web.model.entity.Dish;
import com.litecase.boot.web.model.entity.SetMeal;
import com.litecase.boot.web.service.CategoryService;
import com.litecase.boot.web.service.DishService;
import com.litecase.boot.web.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    SetMealService setMealService;
    @Autowired
    DishService dishService;

    @Override
    public void remove(Long id) {

        LambdaQueryWrapper<SetMeal> setMealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setMealLambdaQueryWrapper.eq(SetMeal::getCategoryId, id);

        Long setMealCount = setMealService.count(setMealLambdaQueryWrapper);

        if(setMealCount > 0) {
            throw new CustomException("该分类关联了套餐，不能删除");
        }

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);

        Long dishCount  = dishService.count(dishLambdaQueryWrapper);

        if (dishCount > 0) {
            throw  new CustomException("该分类关联了菜品，不能删除");
        }

//        super.removeById(id);

        this.removeById(id);

    }
}

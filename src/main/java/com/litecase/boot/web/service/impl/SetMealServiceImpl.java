package com.litecase.boot.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.litecase.boot.web.mapper.SetMealMapper;
import com.litecase.boot.web.model.entity.SetMeal;
import com.litecase.boot.web.service.SetMealService;
import org.springframework.stereotype.Service;

@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, SetMeal> implements SetMealService {
}

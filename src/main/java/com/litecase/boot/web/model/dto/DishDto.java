package com.litecase.boot.web.model.dto;

import com.litecase.boot.web.model.entity.Dish;
import com.litecase.boot.web.model.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;

@Data
public class DishDto extends Dish {
    private  ArrayList<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;
}

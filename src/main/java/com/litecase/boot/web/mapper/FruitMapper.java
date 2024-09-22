package com.litecase.boot.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litecase.boot.web.model.entity.Fruit;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FruitMapper extends BaseMapper<Fruit> {
}

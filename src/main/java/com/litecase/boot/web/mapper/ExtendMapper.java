package com.litecase.boot.web.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface ExtendMapper<T> extends BaseMapper<T> {

    //  java8以后 新增的Default方法是指，在接口内部包含了一些默认的方法实现（也就是接口中可以包含方法体，这打破了Java之前版本对接口的语法限制），从而使得接口在进行扩展的时候，不会破坏与接口相关的实现类代码
    // 参考文章:https://blog.csdn.net/code_my_life/article/details/51891520
    default void selectdOne() {
//        this.selectCount();
    }

//    default T selectOneByField(String field, Object fieldValue) {
//        this.selectOne(Wrapper)(new QueryWrapper()).eq(field, fieldValue);
//        return this.selectOne((Wrapper) (new QueryWrapper()).eq(field, fieldValue));
//
//    }
}

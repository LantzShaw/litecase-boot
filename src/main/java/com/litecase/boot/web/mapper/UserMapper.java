package com.litecase.boot.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.litecase.boot.web.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}

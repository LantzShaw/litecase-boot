package com.litecase.boot.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.litecase.boot.web.mapper.UserMapper;
import com.litecase.boot.web.model.entity.User;
import com.litecase.boot.web.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public void save() {

    }
}

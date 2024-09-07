package com.litecase.boot.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.litecase.boot.web.mapper.UserMapper;
import com.litecase.boot.web.model.entity.User;
import com.litecase.boot.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void save() {
    // this.getById('');

        ArrayList<String> tempArr = new ArrayList<>();

       List<String> temp2 = tempArr.stream().map(item -> {
            return item;
        }).collect(Collectors.toList());

    }

    @Override
    public User findUsername(String username) {
        User user = userMapper.findByUsername(username);

        return user;
    }
}

package com.litecase.boot.web.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.litecase.boot.web.mapper.UserMapper;
import com.litecase.boot.web.model.entity.User;
import com.litecase.boot.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

//    @Override
//    public void save() {
//    // this.getById('');
//
//        ArrayList<String> tempArr = new ArrayList<>();
//
//       List<String> temp2 = tempArr.stream().map(item -> {
//            return item;
//        }).collect(Collectors.toList());
//
//    }

    @Override
    public User findUsername(String username) {
        User user = baseMapper.findByUsername(username);

        return user;
    }

    @Override
    public User register(User user) {
        log.info("add user: {}", user);

        baseMapper.addUser(user);

//        userTestMapper.insert(user);

        return user;
//        userMapper.insert(user);

//        userMapper.insert(user);
    }
}

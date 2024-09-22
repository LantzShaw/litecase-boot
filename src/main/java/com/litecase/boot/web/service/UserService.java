package com.litecase.boot.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.litecase.boot.web.mapper.UserMapper;
import com.litecase.boot.web.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface UserService extends IService<User> {
    User findUsername(String username);

    User register(User user);
}

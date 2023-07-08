package com.litecase.boot.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.litecase.boot.web.model.entity.User;

public interface UserService extends IService<User> {
    void save();
}

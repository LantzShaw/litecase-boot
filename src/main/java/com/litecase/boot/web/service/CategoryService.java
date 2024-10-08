package com.litecase.boot.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.litecase.boot.web.model.entity.Category;
import org.springframework.stereotype.Service;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}

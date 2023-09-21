package com.litecase.boot.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.litecase.boot.web.common.BaseContext;
import com.litecase.boot.web.common.R;
import com.litecase.boot.web.model.entity.Category;
import com.litecase.boot.web.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/add")
    public R<String> save(HttpServletRequest request, @RequestBody Category category) {

//        request.getSession().setAttribute("category", category.getId());
//
//        BaseContext.setCurrentId(category.getId());

        categoryService.save(category);

        return R.success("新增成功!");
    }

    /**
     * 分类列表
     *
     * @param pageIndex
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<Category>> page(int pageIndex, int pageSize, String name) {
        Page<Category> pageInfo = new Page<>(pageIndex, pageSize);

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(Category::getSort);
        queryWrapper.like(StringUtils.isNotEmpty(name), Category::getName, name);

        categoryService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 更新分类
     *
     * @param request
     * @param category
     * @return
     */
    @PutMapping("/update")
    public R<String> update(HttpServletRequest request, @RequestBody Category category) {
        Long categoryId = (Long) request.getSession().getAttribute("category");

        BaseContext.setCurrentId(categoryId);

        categoryService.updateById(category);

        return R.success("更新成功!");
    }

    /**
     * 删除分类
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete")
    public R<String> delete(Long id) {
    // categoryService.removeById(id);

        categoryService.remove(id);

        return R.success("删除成功!");
    }

    /**
     * 根据条件查询分类条件
     *
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(category.getType() != null, Category::getType, category.getType());

        lambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list =  categoryService.list(lambdaQueryWrapper);


        return R.success(list);
    }
}

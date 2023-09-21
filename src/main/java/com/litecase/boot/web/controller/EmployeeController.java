package com.litecase.boot.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.litecase.boot.web.common.BaseContext;
import com.litecase.boot.web.common.R;
import com.litecase.boot.web.model.entity.Employee;
import com.litecase.boot.web.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RequestMapping("/employee")
@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    /**
     * 登录
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody  Employee employee) {
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        if(emp == null) {
            return  R.error("登录失败!");
        }

        if(!emp.getPassword().equals(password)) {
            return R.error("密码错误!");
        }

        request.getSession().setAttribute("employee", emp.getId());

        log.info("Login: {}", employee);

        return R.success(emp);
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
            request.getSession().removeAttribute("employee");

        return  R.success("操作成功!");
    }

    /**
     * 添加员工
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/add")
    public R<String> save(HttpServletRequest request, @RequestBody  Employee employee) {
        Long employeeId =  (Long) request.getSession().getAttribute("employee");

        // 设置默认值
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateBy(employeeId);
//        employee.setUpdateBy(employeeId);

        BaseContext.setCurrentId(employeeId);

        long id= Thread.currentThread().getId();
        log.info("id: {}", id);

        employeeService.save(employee);

        return R.success("新增成功!");

    }

    /**
     * 员工列表
     *
     * @param pageIndex
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<Employee>> page(int pageIndex, int pageSize, String name) {
        log.info("page接口, page = {}, pageSize = {}, name = {}", pageIndex, pageSize, name);

        Page pageInfo = new Page(pageIndex, pageSize);

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);

        employeeService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    public R<String> update(HttpServletRequest request, Employee employee) {
        Long employeeId = (Long) request.getSession().getAttribute("employee");

//        employee.setUpdateBy(employeeId);
//        employee.setUpdateTime(LocalDateTime.now());

        BaseContext.setCurrentId(employeeId);

        long id = Thread.currentThread().getId();
        log.info("id: {}", id);

        employeeService.updateById(employee);

        return R.success("修改成功!");
    }


    /**
     * 根据ID查询员工信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("id 查询");

        Employee employee = employeeService.getById(id);

        return  R.success(employee);
    }


}

package com.litecase.boot.web.common;

import com.litecase.boot.web.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@ResponseBody
@ControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException exception) {

        if(exception.getMessage().contains("Duplicate entity")) {
            String[] split = exception.getMessage().split(" ");

            return R.error(split[2] + "用户已存在");
        }

        log.error(exception.getMessage());
        return R.error("数据库错误!");
    }

    /**
     * 异常处理方法
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException exception) {

        log.error(exception.getMessage());

        return R.success(exception.getMessage());
    }
}

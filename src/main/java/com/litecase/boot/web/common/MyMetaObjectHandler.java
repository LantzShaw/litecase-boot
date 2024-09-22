package com.litecase.boot.web.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 公共字段自动填充
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("插入时公共字段填充....", metaObject);

        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("createdBy", BaseContext.getCurrentId());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updatedBy", BaseContext.getCurrentId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updatedBy", BaseContext.getCurrentId());
    }
}

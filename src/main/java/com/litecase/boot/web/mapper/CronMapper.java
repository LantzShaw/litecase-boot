package com.litecase.boot.web.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CronMapper {
    @Select("select cron from t_scheduled where cron_id = ${id}")
    public String getCronById(String id);
}

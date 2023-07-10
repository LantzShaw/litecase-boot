package com.litecase.boot.web.task;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// 加上@Configuration或者@Component注解让启动类可以扫描到定时任务类
@Configuration
//@EnableScheduling
public class Task1 {
    // 基于@Scheduled注解的定时任务@Scheduled(fixedDelay = 3000)、@Scheduled(fixedRate  = 3000)
    // initialDelay: initialDelay = 10000 表示在容器启动后，延迟10秒后再执行一次定时器
    // 好像不能设置 initialDelay = 10000 @Scheduled(cron = "*/6 * * * * ?", initialDelay = 10000)
    @Scheduled(cron = "*/6 * * * * ?")
    void sayGoodBye() {
        System.out.println("Task1 sayGoodBye");
    }
}

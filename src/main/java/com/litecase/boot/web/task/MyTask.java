package com.litecase.boot.web.task;


import com.litecase.boot.web.mapper.CronMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
//@EnableScheduling
public class MyTask implements SchedulingConfigurer {
    @Autowired
    CronMapper cronMapper;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//        taskRegistrar.addTriggerTask(() -> process(), triggerContext -> {
//            String cron = cronMapper.getCronById("14");
//
//            if(cron.isEmpty()) {
//                System.out.println("cron is null");
//            }
//
//            return new CronTrigger(cron).nextExecution(triggerContext);
//        });
    }

    private void process() {
        System.out.println("----------定时执行---------");
    }

}

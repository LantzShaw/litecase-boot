package com.litecase.boot.web.task;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// @Scheduled执行周期任务会受到上次一个任务的执行时间影响。那么可以开启多线程执行周期任务。
@EnableAsync // 开启多线程
@Component
//@EnableScheduling
public class MultiThreadTask {
    @Async
    @Scheduled(fixedDelay = 1000) // 间隔1秒
    public void first() throws InterruptedException {
        System.out.println("第一个线程定时任务first" + LocalDateTime.now().toLocalTime() + "\r\n线程: " + Thread.currentThread().getName());
        Thread.sleep(1000 * 10);
    }

    @Async
    @Scheduled(fixedDelay = 2000) // 间隔2秒
    public void second() {
        System.out.println("第二个线程定时任务" + LocalDateTime.now().toLocalTime() + "\n线程: " + Thread.currentThread().getName());
    }
}

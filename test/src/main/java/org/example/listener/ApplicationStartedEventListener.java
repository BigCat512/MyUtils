package org.example.listener;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.common.config.taskScheduler.TaskSchedulerService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p>
 *
 * </p>
 *
 * @author author
 * @version 1.0
 * @since 2024/5/15
 */

@Slf4j
@Component
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Resource(name = "asyncServiceExecutor")
    private ThreadPoolTaskExecutor executor;

    @Resource
    private TaskSchedulerService taskSchedulerService;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // 启动后异步初始化
        executor.execute(() -> {
            log.info("启动后异步初始化");
            var parse = DateUtil.parse("2024-05-21 09:48:00");
            taskSchedulerService.add("定时任务123", parse, (taskId) -> {
                System.out.println("预设 2024-05-21 09:32:00 的定时任务【" + taskId + "】已执行");
            });

            var parse2 = DateUtil.parse("2024-05-21 09:49:00");
            taskSchedulerService.add("定时任务321", parse2, (taskId) -> {
                System.out.println("预设 2024-05-21 09:33:00 的定时任务【" + taskId + "】已执行");
            });
            taskSchedulerService.stop("定时任务321");
        });

    }

}

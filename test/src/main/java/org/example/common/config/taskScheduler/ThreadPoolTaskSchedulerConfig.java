package org.example.common.config.taskScheduler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * <p>
 * 异步线程池ThreadPoolExecutor 配置类
 * </p>
 *
 * @author author
 * @version 1.0
 * @since 2024/5/21
 */
@Configuration
public class ThreadPoolTaskSchedulerConfig {

    @Bean(name = "asyncSchedulerExecutor", destroyMethod = "shutdown")
    public ThreadPoolTaskScheduler asyncScheduler() {
        ThreadPoolTaskScheduler syncScheduler = new ThreadPoolTaskScheduler();
        syncScheduler.setPoolSize(5);
        // 这里给线程设置名字，主要是为了在项目能够更快速的定位错误。
        syncScheduler.setThreadGroupName("AsyncScheduler");
        syncScheduler.setThreadNamePrefix("AsyncSchedulerThread-");
        syncScheduler.setAwaitTerminationSeconds(60);
        // 调度器shutdown被调用时等待当前被调度的任务完成
        syncScheduler.setWaitForTasksToCompleteOnShutdown(true);
        syncScheduler.initialize();
        return syncScheduler;
    }
}


package org.example.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static org.example.common.config.web.HeaderInterceptor.ThreadLocalHolder;

/**
 * <p>
 * $
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/4/20
 */
@Slf4j
@Configuration
public class ExecutorConfig {
    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Bean(name = "asyncServiceExecutor", destroyMethod = "shutdown")
    @Primary
    public ThreadPoolTaskExecutor asyncServiceExecutor() {
        log.info("start asyncServiceExecutor");
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        // 自定义线程工厂，给线程设置 UncaughtExceptionHandler 对象实现异常的默认逻辑
        executor.setThreadFactory((Runnable r) -> new Thread(r) {{
            setDefaultUncaughtExceptionHandler((Thread thread1, Throwable e) -> {
                log.error("asyncServiceExecutor-exceptionHandler：" + e.getMessage());
            });
        }});
        // 配置核心线程数
        executor.setCorePoolSize(10);
        // 配置最大线程数
        executor.setMaxPoolSize(20);
        // 配置队列大小
        executor.setQueueCapacity(200);
        // 配置空闲线程存活秒数。默认值为 60。
        // executor.setKeepAliveSeconds(60);
        // 配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix(String.format("%s-%s-async-service-", applicationName, activeProfile));
        // 设置拒绝策略：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

    /**
     * <p>
     * ThreadPoolTaskExecutor的子类
     * <a href="https://www.cnblogs.com/aaacarrot/p/16964508.html">...</a>
     * </p>
     *
     * @author XJH
     * @version Runnable0
     * @since 2023/4/13
     */
    @Slf4j
    public static class VisiableThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

        private void showThreadPoolInfo(String prefix) {
            ThreadPoolExecutor executor = super.getThreadPoolExecutor();
            log.info("{}, {},taskCount [{}], completedTaskCount [{}], activeCount [{}], queueSize [{}]",
                    this.getThreadNamePrefix(),
                    prefix,
                    executor.getTaskCount(),
                    executor.getCompletedTaskCount(),
                    executor.getActiveCount(),
                    executor.getQueue().size());
        }

        @Override
        public void execute(Runnable task) {
            this.showThreadPoolInfo("Runnable do execute");
            super.execute(VisiableThreadPoolTaskExecutor.wrap(task, ThreadLocalHolder.get()));
        }

        @Override
        public void execute(Runnable task, long startTimeout) {
            this.showThreadPoolInfo("Callable do execute");
            super.execute(VisiableThreadPoolTaskExecutor.wrap(task, ThreadLocalHolder.get()), startTimeout);
        }

        @Override
        public Future<?> submit(Runnable task) {
            this.showThreadPoolInfo("Runnable do submit");
            return super.submit(VisiableThreadPoolTaskExecutor.wrap(task, ThreadLocalHolder.get()));
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            this.showThreadPoolInfo("Callable do submit");
            return super.submit(VisiableThreadPoolTaskExecutor.wrap(task, ThreadLocalHolder.get()));
        }

        @Override
        public ListenableFuture<?> submitListenable(Runnable task) {
            this.showThreadPoolInfo("Runnable do submitListenable");
            return super.submitListenable(VisiableThreadPoolTaskExecutor.wrap(task, ThreadLocalHolder.get()));
        }

        @Override
        public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
            this.showThreadPoolInfo("Callable do submitListenable");
            return super.submitListenable(VisiableThreadPoolTaskExecutor.wrap(task, ThreadLocalHolder.get()));
        }

        private static Runnable wrap(Runnable task, String headerValue) {
            return () -> {
                try {
                    ThreadLocalHolder.set(headerValue);
                    task.run();
                } finally {
                    ThreadLocalHolder.remove();
                }
            };
        }

        private static <T> Callable<T> wrap(Callable<T> task, String headerValue) {
            return () -> {
                try {
                    ThreadLocalHolder.set(headerValue);
                    return task.call();
                } finally {
                    ThreadLocalHolder.remove();
                }
            };
        }
    }
}

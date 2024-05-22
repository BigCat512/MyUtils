package org.example.common.config.taskScheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Consumer;


/**
 * <p>
 * 来源 {@link <a href="https://juejin.cn/post/7013234573823705102">宁在春</a>}
 * </p>
 *
 * @author author
 * @version 1.0
 * @since 2024/5/15
 */
@Slf4j
@Component
public class TaskSchedulerService {

    /**
     * 以下两个都是线程安全的集合类。
     */
    private static final Map<String, ScheduledFuture<?>> taskMap = new ConcurrentHashMap<>();

    /**
     * 任务列表
     */
    private static final List<String> taskList = new CopyOnWriteArrayList<>();

    /**
     * 线程池
     */
    @Resource(name = "asyncSchedulerExecutor")
    private ThreadPoolTaskScheduler syncScheduler;

    /**
     * 查看已开启但还未执行的动态任务
     *
     * @return {@link List<String>}
     * @author author
     * @since 2024/5/15
     **/
    public List<String> getTaskList() {
        return taskList;
    }


    /**
     * 添加一个动态任务
     *
     * @param taskId    {@link String}
     * @param startTime {@link Date}
     * @author author
     * @since 2024/5/15
     **/
    public void add(String taskId, Date startTime, Consumer<String> consumer) {
        if (taskMap.containsKey(taskId)) {
            this.stop(taskId);
        }
        /*
         * schedule :调度给定的Runnable ，在指定的执行时间调用它。
         * 一旦调度程序关闭或返回的ScheduledFuture被取消，执行将结束。
         * 参数：
         * 任务 – 触发器触发时执行的 Runnable
         * startTime – 任务所需的执行时间（如果这是过去，则任务将立即执行，即尽快执行）
         */
        var schedule = syncScheduler.schedule(this.getRunnable(taskId, startTime, consumer), startTime);
        taskMap.put(taskId, schedule);
        taskList.add(taskId);
        log.info("新增自定义定时任务：{}，{}", taskId, startTime);
    }


    /**
     * 运行任务
     *
     * @param taskId    {@link String}
     * @param startTime {@link Date}
     * @param consumer  {@link Consumer<String>}
     * @return {@link Runnable}
     * @author author
     * @since 2024/5/15
     **/
    public Runnable getRunnable(String taskId, Date startTime, Consumer<String> consumer) {
        return () -> {
            log.info("任务【{}】定时【{}】开始执行", taskId, startTime);
            try {
                consumer.accept(taskId);
                this.stop(taskId);
            } catch (Exception e) {
                log.info("任务【{}】执行异常：{}", taskId, e.getMessage());
            }
            log.info("任务【{}】执行结束", taskId);
        };
    }

    /**
     * 停止任务
     *
     * @param taskId {@link String}
     * @author author
     * @since 2024/5/15
     **/
    public void stop(String taskId) {
        var future = taskMap.get(taskId);
        if (Objects.isNull(future)) return;
        future.cancel(true);
        taskMap.remove(taskId);
        taskList.remove(taskId);
        log.info("移除自定义定时任务：{}", taskId);
    }

}

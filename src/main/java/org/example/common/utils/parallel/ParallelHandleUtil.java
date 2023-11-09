package org.example.common.utils.parallel;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * <p>
 * 并行处理函数操作
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/9/27
 */
@Slf4j
public class ParallelHandleUtil {

    private ParallelHandleUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 接收集合，并行处理函数操作
     *
     * @param collection {@link List <E>}
     * @param consumer   {@link ParallelFunction<E, R>}
     * @return {@link  List<R>}
     **/
    @SneakyThrows
    public static <E, R> List<R> handle(List<E> collection, ParallelFunction<E, R> consumer) {
        ThreadPoolTaskExecutor executor = SpringUtil.getBean("asyncServiceExecutor");
        if (CollUtil.isEmpty(collection)) return Collections.emptyList();
        CountDownLatch countDownLatch = new CountDownLatch(collection.size());
        List<R> list = new ArrayList<>(Collections.nCopies(collection.size(), null));
        for (int i = 0; i < collection.size(); i++) {
            int finalI = i;
            executor.execute(() -> {
                try {
                    list.set(finalI, consumer.accept(finalI, collection));
                } catch (Exception e) {
                    log.info("并行处理函数操作发生异常：{}，{}", finalI, e.getMessage());
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        return list;
    }

}

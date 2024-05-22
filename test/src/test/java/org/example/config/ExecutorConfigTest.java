package org.example.config;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.Future;

@SpringBootTest
class ExecutorConfigTest {
    @Resource(name = "asyncServiceExecutor")
    private ThreadPoolTaskExecutor executor;

    @Test
    @SneakyThrows
    void test() {
        executor.execute(() -> System.out.println("大江东去"));
        Future<String> submit = executor.submit(() -> "大江西来");
        System.out.println(submit.get());
        executor.execute(() -> System.out.println(1 / 0));
    }

}

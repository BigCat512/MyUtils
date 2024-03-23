package org.example.common.config.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 异步初始化bean配置注入
 * {@link  /docs/images/异步初始化bean示例.png}
 * <a href="https://mp.weixin.qq.com/s/zXAncSrthmbaxQNkfJdNKg">why技术-异步初始化bean</a>
 * </p>
 *
 * @author why技术
 * @version 1.0
 * @since 2023/6/5
 */
@Configuration
public class AsyncBeanInitConfig {
    @Bean
    public AsyncTaskExecutionListener asyncTaskExecutionListener() {
        return new AsyncTaskExecutionListener();
    }
}

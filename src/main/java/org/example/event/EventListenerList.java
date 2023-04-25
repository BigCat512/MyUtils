package org.example.event;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 事件监听
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/4/25
 */
@Slf4j
@Component
public class EventListenerList {
    @Resource
    private UserMapper userMapper;
    @Resource
    DataSourceTransactionManager dataSourceTransactionManager;
    @Resource
    TransactionDefinition transactionDefinition;

    /**
     * 用户注册事件监听
     *
     * 贴事务注解和手动事务均无效
     *
     * @param user {@link User}
     * @author XJH
     * @since 2023/4/25
     **/
    @Order(1) // 一个事件多个事监听，同步的情况下，使用@order值越小，执行顺序优先
    @Async("asyncServiceExecutor")
    @EventListener
    public void userRegisterListener(User user) {
        TransactionStatus transactionStatus = null;
        try {
            // 手动开启事务
            transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
            log.info("userRegisterListener：{}", JSONUtil.toJsonStr(user));
            int insert = userMapper.insert(user);
            int i = 1 / 0;
            // 手动提交事务
            dataSourceTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            // 手动回滚事务
            if (Objects.nonNull(transactionStatus)) {
                dataSourceTransactionManager.rollback(transactionStatus);
            }
        }
    }
}

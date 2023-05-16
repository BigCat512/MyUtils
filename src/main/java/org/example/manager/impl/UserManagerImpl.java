package org.example.manager.impl;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.User;
import org.example.manager.UserManager;
import org.example.mapper.UserMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/4/25
 */
@Slf4j
@Service
public class UserManagerImpl implements UserManager {
    @Resource
    private UserMapper userMapper;
    @Resource(name = "asyncServiceExecutor")
    private ThreadPoolTaskExecutor executor;
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    @Resource
    DataSourceTransactionManager dataSourceTransactionManager;
    @Resource
    TransactionDefinition transactionDefinition;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBatch(List<User> users) {
        userMapper.insertBatch(users);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(User user) {
        int insert = userMapper.insert(user);
        executor.execute(() -> {
            UserManagerImpl bean = SpringUtil.getBean(UserManagerImpl.class);
            bean.createThreadSave(new User().setName("save2").setAge(11));
        });

        return insert;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public Integer save2(User user) {
        int insert = userMapper.insert(user);
        applicationEventPublisher.publishEvent(new User().setName("testSaveEvent20230516.save2.event").setAge(11));
        return insert;
    }

    @Override
    public Integer save3(User user) {
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
        return null;
    }

    // =========================================== private method ===========================================


    /**
     * 测试多线程调用本类方法的事务
     * @param user {@link User}
     * @return {@link Integer}
     * @author XJH
     * @since 2023/4/25
     **/
    @Transactional(rollbackFor = Exception.class)
    public Integer createThreadSave(User user) {
        int insert = userMapper.insert(user);
        int i = 1 / 0;
        return insert;
    }

}

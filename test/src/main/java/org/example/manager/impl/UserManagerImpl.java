package org.example.manager.impl;

import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.service.UserService;
import org.example.domain.dto.UserDTO;
import org.example.manager.UserManager;
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
    private UserService userService;
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
    public void saveBatch(List<UserDTO> users) {
        // userService.insertBatch(users);
    }


    @Transactional(rollbackFor = Exception.class)
    public boolean save1(UserDTO user) {
        boolean insert = userService.insert(user);
        executor.execute(() -> {
            UserManagerImpl bean = SpringUtil.getBean(UserManagerImpl.class);
            bean.createThreadSave(UserDTO.builder().name("save2").age(11).build());
        });

        return insert;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public boolean save2(UserDTO user) {
        boolean insert = userService.insert(user);
        applicationEventPublisher.publishEvent(UserDTO.builder().name("testSaveEvent20230516.save2.event").age(11).build());
        return insert;
    }

    @Override
    public boolean save3(UserDTO user) {
        TransactionStatus transactionStatus = null;
        try {
            // 手动开启事务
            transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
            log.info("userRegisterListener：{}", JSONUtil.toJsonStr(user));
            boolean insert = userService.insert(user);
            int i = 1 / 0;
            // 手动提交事务
            dataSourceTransactionManager.commit(transactionStatus);
        } catch (Exception e) {
            // 手动回滚事务
            if (Objects.nonNull(transactionStatus)) {
                dataSourceTransactionManager.rollback(transactionStatus);
            }
        }
        return Boolean.FALSE;
    }

    // =========================================== private method ===========================================


    /**
     * 测试多线程调用本类方法的事务
     *
     * @param user {@link UserDTO}
     * @return {@link Integer}
     * @author XJH
     * @since 2023/4/25
     **/
    @Transactional(rollbackFor = Exception.class)
    public boolean createThreadSave(UserDTO user) {
        boolean insert = userService.insert(user);
        int i = 1 / 0;
        return insert;
    }

}

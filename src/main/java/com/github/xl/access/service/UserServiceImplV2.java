package com.github.xl.access.service;

import com.github.xl.access.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2019/04/15 20:13
 * <p>
 * 未使用T注解的方法调用T注解的方法，不会开启事务
 */
@Service("V2")
public class UserServiceImplV2 implements UserService {

    private final UserDao userDao;

    private final PlatformTransactionManager txManager;

    @Autowired
    public UserServiceImplV2(UserDao userDao, @Qualifier("globalTxManager") PlatformTransactionManager txManager) {
        this.userDao = userDao;
        this.txManager = txManager;
    }


    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
    @Override
    public void required() throws Exception {
        logic();
        throw new Exception("required() from V2");
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void requiresNew() throws Exception {
        logic();
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.NESTED)
    @Override
    public void nested() throws Exception {
        logic();
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public void notSupport() throws Exception {
        logic();
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.SUPPORTS)
    @Override
    public void supports() throws Exception {
        logic();
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.MANDATORY)
    @Override
    public void mandatory() throws Exception {
        logic();
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.NEVER)
    @Override
    public void never() throws Exception {
        logic();
    }

    @Override
    public void nothing() throws Exception {
//        logic();
        TransactionDefinition definition = new DefaultTransactionDefinition();
        TransactionStatus status = txManager.getTransaction(definition);

        try {
            userDao.addUser(35);
            txManager.commit(status);
        } catch (Exception e) {
            txManager.rollback(status);
            throw e;
        }
    }

    private void logic() {
        userDao.addUser(2);
    }
}

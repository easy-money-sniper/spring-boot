package com.github.xl.access.service;

import com.github.xl.access.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2019/04/15 20:13
 * <p>
 */
@Service("V1")
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserService userServiceImplV2;

    @Autowired
    public UserServiceImpl(UserDao userDao, @Qualifier("V2") UserService userServiceImplV2) {
        this.userDao = userDao;
        this.userServiceImplV2 = userServiceImplV2;
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRED)
    @Override
    public void required() throws Exception {
        doSomethingA();
        userServiceImplV2.required();
        doSomethingB();
        throw new Exception("exception from required");
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void requiresNew() throws Exception {
        doSomethingA();
        userServiceImplV2.requiresNew(); // 跨Service层调用，外部事务异常不会导致内部事务的回滚
        doSomethingB();
        throw new Exception("exception from requiresNew");
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.NESTED)
    @Override
    public void nested() throws Exception {
        doSomethingA();
        userServiceImplV2.nested();
        doSomethingB();
        throw new Exception("exception from nested");
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void notSupport() throws Exception {
        doSomethingA();
        userServiceImplV2.notSupport();
        doSomethingB();
    }

    @Transactional(rollbackFor = Throwable.class, propagation = Propagation.SUPPORTS)
    @Override
    public void supports() throws Exception {
        doSomethingA();
        userServiceImplV2.supports();
        doSomethingB();
    }

    // @Transactional(rollbackFor = Throwable.class, propagation = Propagation.MANDATORY)
    @Override
    public void mandatory() throws Exception {
        doSomethingA();
        userServiceImplV2.mandatory();
        doSomethingB();
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void never() throws Exception {
        doSomethingA();
        userServiceImplV2.never();
        doSomethingB();
    }

    @Override
    public void nothing() throws Exception {
        // no @T calls @T 不会发生事务，内部事务异常不会回滚
        doSomethingA();
        userServiceImplV2.required();
        doSomethingB();
    }

    @Transactional
    public void doSomethingA() throws Exception {
        userDao.addUser(1);
    }

    private void doSomethingB() throws Exception {
        userDao.addUser(3);
    }
}

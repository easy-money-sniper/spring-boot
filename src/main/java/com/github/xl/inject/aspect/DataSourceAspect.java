package com.github.xl.inject.aspect;

import com.github.xl.inject.DynamicDataSource;
import com.github.xl.inject.config.annotation.UseMaster;
import com.github.xl.inject.config.annotation.UseSlave;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2018/11/01 16:37
 * 配置读写分离
 */
@Aspect
@Component
@EnableAspectJAutoProxy//(proxyTargetClass = true, exposeProxy = true)
// proxyTargetClass指定使用CGLIB代理 exposeProxy 是否暴露代理对象
public class DataSourceAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceAspect.class);

    @Around("@annotation(useMaster)")
    public Object doAroundMaster(ProceedingJoinPoint joinPoint, UseMaster useMaster) throws Throwable {
        if (null == useMaster) {
            LOGGER.warn("annotation [UseMaster] not set, will use master as default");
        }
        DynamicDataSource.useMaster();
        try {
            return joinPoint.proceed();
        } finally {
            DynamicDataSource.clear();
        }
    }

    @Around("@annotation(useSlave)")
    public Object doAroundSlave(ProceedingJoinPoint joinPoint, UseSlave useSlave) throws Throwable {
        if (null == useSlave) {
            LOGGER.warn("annotation [UseSlave] not set, wil use master as default");
            DynamicDataSource.useMaster();
        } else {
            DynamicDataSource.useSlave();
        }
        try {
            return joinPoint.proceed();
        } finally {
            DynamicDataSource.clear();
        }
    }

}

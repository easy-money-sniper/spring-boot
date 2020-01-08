package com.github.xl.access.service;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2019/04/15 20:13
 */
public interface UserService {

    void required() throws Exception;

    void requiresNew() throws Exception;

    void nested() throws Exception;

    void notSupport() throws Exception;

    void supports() throws Exception;

    void mandatory() throws Exception;

    void never() throws Exception;

    void nothing() throws Exception;
}

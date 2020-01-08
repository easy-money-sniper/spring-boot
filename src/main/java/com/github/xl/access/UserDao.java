package com.github.xl.access;

import com.github.xl.access.model.UserDO;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2018/11/02 14:24
 */
public interface UserDao {

    UserDO getUserById(Long id);

    void addUser(int age);
}

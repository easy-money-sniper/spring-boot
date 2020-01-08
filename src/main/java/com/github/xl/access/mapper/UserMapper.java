package com.github.xl.access.mapper;

import com.github.xl.access.model.UserDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2018/11/02 14:27
 */
@Repository
public interface UserMapper {
    UserDO getUserById(@Param("id") long id);

    void addUser(@Param("age") int age);
}

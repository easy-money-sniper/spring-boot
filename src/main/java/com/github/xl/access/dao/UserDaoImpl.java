package com.github.xl.access.dao;

import com.github.xl.access.UserDao;
import com.github.xl.access.model.UserDO;
import com.github.xl.inject.config.annotation.UseMaster;
import com.github.xl.inject.config.annotation.UseSlave;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2018/11/02 14:26
 */
//@Component
//public class UserDaoImpl implements UserDao {
//    private final UserMapper userMapper;
//
//    @Autowired
//    public UserDaoImpl(UserMapper userMapper) {
//        this.userMapper = userMapper;
//    }
//
//    @UseSlave
//    @Override
//    public long countUser() {
//        return userMapper.countUser();
//    }
//}
@Component
public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {

    @Autowired
    @Override
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    @UseSlave
    @Override
    public UserDO getUserById(Long id) {
        Map<String, Object> params = new HashMap<String, Object>(1) {{
            put("id", id);
        }};
        return getSqlSession().selectOne("UserMapper.getUserById", params);
    }
}

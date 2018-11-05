package com.github.xl.inject.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.github.xl.inject.DBRole;
import com.github.xl.inject.DynamicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2018/11/01 14:57
 */
@Configuration
@EnableTransactionManagement
//MapperScan + interface Mapper + namespace define Mapper interface
//@MapperScan(basePackages = "com.github.xl.access.mapper")
public class MyBatisConfig {
    /**
     * 配置主从库
     */
    @Bean
    public DynamicDataSource dynamicDataSource(
            @Autowired @Qualifier("masterDataSource") DataSource masterDataSource,
            @Autowired @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<Object, Object>() {{
            put(DBRole.MASTER.name(), masterDataSource);
            put(DBRole.SLAVE.name(), slaveDataSource);
        }};
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDataSource;
    }

    /**
     * druid使用
     */
//    @Bean(name = "masterDataSource", initMethod = "init", destroyMethod = "close")
//    @ConfigurationProperties(prefix = "spring.datasource.druid")
//    public DruidDataSource masterDataSource() {
//        return new DruidDataSource();
//    }
//
//    @Bean(name = "slaveDataSource", initMethod = "init", destroyMethod = "close")
//    @ConfigurationProperties(prefix = "spring.datasource.druid")
//    public DruidDataSource slaveDataSource() {
//        return new DruidDataSource();
//    }

    /**
     * 结合druid-spring-boot-starter使用 配置多数据源
     */
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource masterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
    public DataSource slaveDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 配置事务管理
     */
    @Bean
    public PlatformTransactionManager platformTransactionManager(DynamicDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * Mybatis SqlSessionFactoryBean
     * 配置数据源和mybatis-config.xml path
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DynamicDataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));
        return sqlSessionFactoryBean;
    }
}


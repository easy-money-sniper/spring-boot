package com.github.xl.inject.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2018/11/01 10:51
 */
@Component
@ConfigurationProperties(prefix = "my")
@Validated
public class LiangXuConfig {
    @NotEmpty
    private String realName;
    private String age;
    private String height;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}

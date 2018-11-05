package com.github.xl.inject.config.annotation;

import java.lang.annotation.*;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2018/11/01 14:58
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseMaster {
}

package com.github.xl.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2019/07/16 17:01
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> defaultErrorHandler(HttpServletRequest request, Exception e) {

        return new HashMap<String, Object>() {{
            put(e.getClass().getName(), e.getMessage());
        }};
    }
}

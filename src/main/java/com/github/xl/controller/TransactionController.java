package com.github.xl.controller;

import com.github.xl.access.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Liang Xu E-Mail: xuliang5@xiaomi.com Date: 2020/01/08 14:03
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final UserService userService;

    @Autowired
    public TransactionController(@Qualifier("V1") UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/required")
    public void required() throws Exception {
        userService.required();
    }

    @GetMapping("/requiresNew")
    public void requiresNew() throws Exception {
        userService.requiresNew();
    }

    @GetMapping("/supports")
    public void supports() throws Exception {
        userService.supports();
    }

    @GetMapping("/notSupport")
    public void notSupport() throws Exception {
        userService.notSupport();
    }

    @GetMapping("/mandatory")
    public void mandatory() throws Exception {
        userService.mandatory();
    }

    @GetMapping("/never")
    public void never() throws Exception {
        userService.never();
    }

    @GetMapping("/nested")
    public void nested() throws Exception {
        userService.nested();
    }

    @GetMapping("/nothing")
    public void nothing() throws Exception {
        userService.nothing();
    }
}

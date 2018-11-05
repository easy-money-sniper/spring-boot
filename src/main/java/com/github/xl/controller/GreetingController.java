package com.github.xl.controller;

import com.github.xl.access.UserDao;
import com.github.xl.inject.config.LiangXuConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    private final LiangXuConfig config;
    private UserDao userDao;

    @Autowired
    public GreetingController(LiangXuConfig config, UserDao userDao) {
        this.config = config;
        this.userDao = userDao;
    }

    @GetMapping("/hi/{id:[0-9]+}")
    public String greeting(@PathVariable Long id) {
        return config.getRealName() + " " + id + " "
                + userDao.getUserById(id);
    }
}

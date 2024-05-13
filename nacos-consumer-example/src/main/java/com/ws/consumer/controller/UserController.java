package com.ws.consumer.controller;

import com.ws.consumer.config.UserConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserConfig userConfig;

    @GetMapping("/get")
    public Object get(){
        UserConfig user = new UserConfig();
        user.setName(userConfig.getName());
        user.setAge(userConfig.getAge());
        return user;
    }
}

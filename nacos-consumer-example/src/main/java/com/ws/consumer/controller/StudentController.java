package com.ws.consumer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
@RefreshScope
public class StudentController {

    @Value("${student.username}")
    private String username;

    @Value("${student.password}")
    private String password;

    @RequestMapping("/get")
    public String get() {
        return String.format("用户名：" + username + "，密码：" + password);
    }
}

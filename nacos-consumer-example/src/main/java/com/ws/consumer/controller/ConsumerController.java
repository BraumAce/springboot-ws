package com.ws.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/get")
public class ConsumerController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("{str}")
    public String get(@PathVariable String str) {
        return restTemplate.getForObject("http://nacos-provider-example/get/" + str, String.class);
    }
}

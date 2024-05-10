package com.ws.websocketserver.controller;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("discovery")
public class ServiceDiscoveryController {

    @NacosInjected
    private NamingService namingService;

    @GetMapping("registerService")
    public void registerService() throws NacosException {
        Instance instance = new Instance();
        instance.setIp("13.11.11.11");
        instance.setPort(8082);

        namingService.registerInstance("userRegister", instance);
    }

    @GetMapping("getAllInstances")
    public void getAllInstances() throws NacosException {
        log.info(String.valueOf(namingService.getAllInstances("userRegister")));
    }
}

package com.ws.nacosconfig;

import com.alibaba.nacos.api.config.ConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NacosConfig {

    @Autowired
    private ConfigService configService;

    @Autowired
    private NacosConfigProperties properties;

    public void loadConfig() {
        try {
            String dataId = "example.config.dataId";
            String group = "example.config.group";
            String config = configService.getConfig(dataId, group, 5000);
            log.info("Nacos 配置加载: {}", config);
        } catch (Exception e) {
            log.error("加载 Nacos 配置错误", e);
        }
    }
}



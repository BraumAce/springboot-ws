package com.ws.nacosconfig;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties
public class NacosConfigProperties {

    private String serverAddr;

    private String namespace;
}

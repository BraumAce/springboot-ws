package com.ws.consumer.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Data
@Component
@RefreshScope
public class UserConfig {

    @Value("${user.username}")
    private String name;

    @Value("${user.age}")
    private Integer age;


}

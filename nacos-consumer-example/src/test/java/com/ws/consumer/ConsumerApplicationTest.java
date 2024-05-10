package com.ws.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConsumerApplicationTest {
    @Autowired
    private UserProperties userProperties;

    @Test
    void contextLoads() {
        System.out.println("name: " + userProperties.getName() + "  age: " + userProperties.getAge());
    }
}

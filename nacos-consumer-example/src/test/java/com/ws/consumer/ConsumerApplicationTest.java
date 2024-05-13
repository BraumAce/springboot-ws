package com.ws.consumer;

import com.ws.consumer.config.StudentConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConsumerApplicationTest {

    @Autowired
    private StudentConfig studentProperties;

    @Test
    void contextLoads(){
        System.out.println("name: " + studentProperties.getUsername() +
                ", password: " + studentProperties.getPassword());
    }
}
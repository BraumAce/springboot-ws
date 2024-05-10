package com.ws.websocketserver.controller;

import com.ws.websocketserver.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

@Slf4j
@RestController
@RequestMapping("test")
public class WebSocketController {

    @Resource
    WebSocketServer webSocketServer;

    @GetMapping("/send/ws/{userId}")
    public String sendWS(@PathVariable String userId) {
        try {
            webSocketServer.sendMessage("这是一个来自服务端的消息", new HashSet<>(Arrays.asList(String.valueOf(userId))));
            return "success";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

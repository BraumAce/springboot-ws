package com.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 消息发送控制器
 */
@RestController
public class WebSocketController {

    @Resource
    private SimpMessagingTemplate template;

    /**
     * 单点发送
     * 主题模式: /topic/<topicName>
     * @param params 消息参数
     */
    @MessageMapping("sendToUser")
    public void sendToUser(Map<String, String> params) {
        String fromUserId = params.get("fromUserId");
        String toUserId = params.get("toUserId");
        String msg = "来自用户" + fromUserId + "的消息：" + params.get("msg");
        String destination = "/topic/user" + toUserId;
        template.convertAndSend(destination, msg);
    }

    /**
     * 群发消息
     * @param params 消息参数
     */
    @MessageMapping("/sendToAll")
    public void sendToAll(Map<String, String> params) {
        String fromUserId = params.get("fromUserId");
        String msg = "来自用户" + fromUserId + "的广播：" + params.get("msg");
        String destination = "/topic/chat";
        template.convertAndSend(destination, msg);
    }

}

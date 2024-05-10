package com.ws.websocketserver.handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class WebSocketTextHandler extends TextWebSocketHandler {

    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("收到消息: " + payload);
        session.sendMessage(new TextMessage("输出: " + payload));
    }
}

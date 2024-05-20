package com.ws.websocketserver.handler;

import org.springframework.web.socket.*;

/**
 * WebSocket 服务实现
 */
public class MyWebSocketHandler implements WebSocketHandler {
    /**
     * 建立连接后初始化会话
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Connection established: " + session.getId());
        // 发送初始化消息
        session.sendMessage(new TextMessage("Welcome to the WebSocket service!"));
    }

    /**
     * 处理从客户端接收到的消息
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (message instanceof TextMessage) {
            String msg = ((TextMessage) message).getPayload();
            System.out.println("Received message: " + msg);
            // 回复客户端
            session.sendMessage(new TextMessage("Echo: " + msg));
        } else {
            System.out.println("Unsupported message type: " + message.getClass().getName());
        }
    }

    /**
     * 处理传输过程中发生的错误
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("Transport error: " + exception.getMessage());
        session.close(CloseStatus.SERVER_ERROR);
    }

    /**
     * 连接关闭后的清理工作
     * @param session
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        System.out.println("Connection closed for " + session.getId() + " with status: " + closeStatus);
    }

    /**
     * 指示处理器是否支持部分消息的传输，默认为false
     * @return
     */
    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}

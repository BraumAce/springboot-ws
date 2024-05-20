package com.ws.websocketclient.component;

import javax.websocket.*;
import java.net.URI;
import java.util.concurrent.CountDownLatch;

@ClientEndpoint
public class WebSocketClient {

    private Session session;

    private CountDownLatch latch;

    public WebSocketClient(URI endpointURI) {
        try {
            this.latch = new CountDownLatch(1);
            ContainerProvider.getWebSocketContainer().connectToServer(this, endpointURI);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("已连接 WebSocket 服务端");
        this.session = session;
        this.latch.countDown();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("收到来自服务端的消息: " + message);
    }

    @OnError
    public void onError(Throwable t) {
        System.out.println("客户端发生错误: " + t.getMessage());
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("连接关闭, 因为: " + reason.getReasonPhrase());
        this.session = null;
        this.latch.countDown();
    }

    public void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
    }


}

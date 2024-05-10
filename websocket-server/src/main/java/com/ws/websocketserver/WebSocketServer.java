package com.ws.websocketserver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@ServerEndpoint("/user/{userId}")
public class WebSocketServer {

    // 刷新，每次初始为0
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    // 网络套接字设置，set 用来存放每个客户端对应的 WebSocket 对象
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    // 会话
    private Session session;

    // id
    private String userId = "";

    @OnOpen
    public void onOpen(Session session, @PathParam("userId")String userId) {
        this.session = session;
        webSocketSet.add(this);
        this.userId = userId;
        addOnlineCount();
        log.info("有新客户端开始监听，userId = " + userId + "，当前在线人数为：" + getOnlineCount());
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        subOnlineCount();
        // 断开连接，更新占用情况
        log.info("释放的 userId = " + userId + " 的客户端");
        releaseResource();
    }

    private void releaseResource() {
        log.info("有一条连接关闭！当前在线人数为: " + getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("收到来自客户端 userId = " + userId + " 的信息: " + message);
        // 群发消息
        HashSet<String> userIds = new HashSet<>();
        for (WebSocketServer item : webSocketSet){
            userIds.add(item.userId);
        }
        try{
            sendMessage("客户端" + this.userId + "发布消息: " + message, userIds);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error(session.getBasicRemote() + "客户端发送错误");
        error.printStackTrace();
    }

    public void sendMessage(String message, HashSet<String> toSids) throws IOException{
        log.info("推送消息到客户端 " + toSids + ", 推送内容为: " + message);

        for (WebSocketServer item : webSocketSet) {
            try {
                // 可以只推送给传入的客户端，也可以全部都推送
                if (toSids.size() <= 0) {
                    item.sendMessage(message);
                } else if (toSids.contains(item.userId)) {
                    item.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    private static int getOnlineCount() {
        return onlineCount.get();
    }

    private static void addOnlineCount() {
        onlineCount.getAndIncrement();
    }

    private static void subOnlineCount() {
        onlineCount.getAndDecrement();
    }
}

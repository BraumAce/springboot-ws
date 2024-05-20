package com.websocket.config;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 会话连接池
 */
public class SessionPool {

    // 用 Map 存放每个客户端对应的 WebSocket 对象
    public static Map<String, Session> sessions = new ConcurrentHashMap<>();

    /**
     * 关闭连接，从连接池中删除客户端
     * @param sessionId    会话id
     * @throws IOException 抛出异常
     */
    public static void close(String sessionId) throws IOException {
        for (String userId : SessionPool.sessions.keySet()) {
            Session session = SessionPool.sessions.get(userId);
            if (session.getId().equals(sessionId)) {
                sessions.remove(userId);
                break;
            }
        }
    }

    /**
     * 群发消息
     * @param message 消息内容
     */
    public static void sendMessage(String userId, String message) {
        message = "来自用户" + userId + "的广播：" + message;
        for (String sessionId : SessionPool.sessions.keySet()) {
            if (!sessionId.equals(userId)) {
                SessionPool.sessions.get(sessionId).getAsyncRemote().sendText(message);
            }
        }
    }

    /**
     * 单点发送消息
     * @param fromUserId 消息发送者
     * @param toUserId   消息接收者
     * @param message    消息内容
     */
    public static void sendMessage(String fromUserId, String toUserId, String message) {
        Session session;
        // 如果-1则表示发送消息给服务端，服务端收到进行反馈
        if (toUserId.equals("-1")) {
            session = sessions.get(fromUserId);
            message = "服务端收到用户" + fromUserId + "的消息：" + message;
        } else {
            session = sessions.get(toUserId);
            message = "收到用户" + fromUserId + "的消息：" + message;
        }

        if (session != null) {
            session.getAsyncRemote().sendText(message);
        } else {
            session = sessions.get(fromUserId);
            session.getAsyncRemote().sendText("用户" + toUserId + "不存在");
        }
    }
}

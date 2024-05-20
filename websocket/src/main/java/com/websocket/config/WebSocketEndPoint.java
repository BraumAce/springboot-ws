package com.websocket.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@ServerEndpoint(value = "/websocket/{userId}")
public class WebSocketEndPoint {

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     * @param session
     * @param userId
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        // 把会话存入到连接池中
        SessionPool.sessions.put(userId, session);
    }

    /**
     * 关闭连接
     * @param session
     * @throws IOException
     */
    @OnClose
    public void onClose(Session session) throws IOException {
        SessionPool.close(session.getId());
        session.close();
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端的消息:" + message);
        // 如果是心跳检测的消息，则返回pong作为心跳回应
        if (message.equalsIgnoreCase("ping")) {
            try {
                Map<String, Object> params = new HashMap<>();
                params.put("type", "pong");
                //session.getBasicRemote().sendText(JSON.toJSONString(params));
                log.info("应答客户端的消息:" + JSON.toJSONString(params));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //  {"fromUserId": userId,"toUserId": toUserId,"msg": msg};
            Map<String, Object> params = JSON.parseObject(message, new HashMap<String, Object>().getClass());
            String fromId = getStringValue(params, "fromUserId");
            String toUserId = getStringValue(params, "toUserId");
            String msg = getStringValue(params, "msg");
            if (toUserId != null && !toUserId.equals("all")) {
                // 点对点发送消息
                SessionPool.sendMessage(fromId, toUserId, msg);
            } else {
                // 群发消息
                SessionPool.sendMessage(fromId, msg);
            }
        }
    }

    private String getStringValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        return (value != null) ? value.toString() : null;
    }

}

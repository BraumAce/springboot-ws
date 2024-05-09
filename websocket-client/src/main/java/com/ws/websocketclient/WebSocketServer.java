package com.ws.websocketclient;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ServerEndpoint("/user/{userId}")
public class WebSocketServer {

    private static AtomicInteger onlineCount = new AtomicInteger(0);


}

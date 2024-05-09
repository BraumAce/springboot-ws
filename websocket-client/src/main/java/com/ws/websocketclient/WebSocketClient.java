package com.ws.websocketclient;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnError;
import javax.websocket.Session;

@ClientEndpoint
public class WebSocketClient {

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to WebSocket server");
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Received message from server: " + message);
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }

    public void sendMessage(String message) {
        this.session.getAsyncRemote().sendText(message);
    }

    // Other methods...
}

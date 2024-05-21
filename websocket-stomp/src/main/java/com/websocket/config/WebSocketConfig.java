package com.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocketConfig 配置类
 * 注入对象 ServerEndpointExporter
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry){
        //客户端连接端点
        registry.addEndpoint("/websocket")
                //.setAllowedOrigins("*")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    /**
     * 配置消息交换机
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //registry.enableSimpleBroker("/topic/","/queue/");
        registry.enableStompBrokerRelay("/topic/","/queue/","/exchange/")
                .setRelayHost("localhost")
                .setRelayPort(61613)
                .setClientLogin("guest")
                .setClientPasscode("guest")
                .setVirtualHost("/");
        registry.setApplicationDestinationPrefixes("/app");
    }

}

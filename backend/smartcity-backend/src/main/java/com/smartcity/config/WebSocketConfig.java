package com.smartcity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue"); // broker destinations
        config.setApplicationDestinationPrefixes("/app"); // app destinations (not used for server push here)
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // SockJS endpoint that the frontend will connect to
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // or restrict to your front-end origin
                .withSockJS();
    }
}

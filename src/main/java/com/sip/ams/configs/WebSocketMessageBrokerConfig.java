package com.sip.ams.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry; 
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketMessageBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
		// Use the built-in message broker for subscriptions and broadcasting and route
		// messages whose destination header begins with /topic or /queue to the broker.
        config.enableSimpleBroker("/topic", "/queue"); // Prefix for server-side broadcasts
        
		// STOMP messages whose destination header begins with /app are routed to
		// @MessageMapping methods in @Controller classes.
        config.setApplicationDestinationPrefixes("/app"); // Prefix for client-side messages
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
		// /ws is the HTTP URL for the endpoint to which a WebSocket (or SockJS) client
		// needs to connect for the WebSocket handshake.
        registry.addEndpoint("/ws").withSockJS(); // Enables WebSocket endpoint with SockJS fallback
        registry.setPreserveReceiveOrder(true); // To enable ordered publishing
    } 
    
}

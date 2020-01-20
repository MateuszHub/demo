package uj.mateusz.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import uj.mateusz.demo.controller.ChatController;
import uj.mateusz.demo.handler.ChatHandler;
import uj.mateusz.demo.services.WebsocketSessionsManager;

@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    private WebsocketSessionsManager WSM;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ChatHandler(WSM), "/chat").setAllowedOrigins("*");
    }


}

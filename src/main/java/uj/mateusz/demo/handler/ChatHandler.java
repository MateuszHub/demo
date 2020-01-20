package uj.mateusz.demo.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import uj.mateusz.demo.controller.ChatController;
import uj.mateusz.demo.services.WebsocketSessionsManager;

import java.io.IOException;

public class ChatHandler extends TextWebSocketHandler {
    private WebsocketSessionsManager WSM;

    public ChatHandler(WebsocketSessionsManager WSM) {
        super();
        this.WSM = WSM;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        this.WSM.registerSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        this.WSM.unregisterSession(session);
        super.afterConnectionClosed(session, status);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        this.WSM.getSessions().forEach(s -> {
            this.sendMessage(s, message);
        });
    }

    private boolean sendMessage(WebSocketSession wss, TextMessage message) {
        try {
            wss.sendMessage(message);
            return true;
        } catch (IOException e) {
            return false;
        }
    }


}

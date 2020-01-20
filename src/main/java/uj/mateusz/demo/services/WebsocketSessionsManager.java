package uj.mateusz.demo.services;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;

@Service
@Scope("singleton")
public class WebsocketSessionsManager {
    private HashSet<WebSocketSession> sessions;
    public WebsocketSessionsManager() {
        this.sessions = new HashSet<>();
    }

    public synchronized void registerSession(WebSocketSession session) {
        this.sessions.add(session);
    }

    public synchronized void unregisterSession(WebSocketSession session) {
        this.sessions.remove(session);
    }

    public HashSet<WebSocketSession> getSessions() {
        return this.sessions;
    }

}

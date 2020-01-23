package uj.mateusz.demo.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import uj.mateusz.demo.controller.ChatController;
import uj.mateusz.demo.entitiy.ChatMessage;
import uj.mateusz.demo.repository.ChatMessageRepository;
import uj.mateusz.demo.services.UserSecurityService;
import uj.mateusz.demo.services.WebsocketSessionsManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatHandler extends TextWebSocketHandler {
    private WebsocketSessionsManager WSM;
    private UserSecurityService uss;
    private ChatMessageRepository cmr;

    public ChatHandler(WebsocketSessionsManager WSM, UserSecurityService uss, ChatMessageRepository cmr) {
        super();
        this.WSM = WSM;
        this.uss = uss;
        this.cmr = cmr;
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
        this.uss.authenticateToken(parseJson(message.getPayload(), "token").replace("\"", ""));
        if (this.uss.hasRole("user")) {
            String msg = parseJson(message.getPayload(), "content");
            var cm = saveMessage(msg);
            this.WSM.getSessions().forEach(s -> {
                this.sendMessage(s, new Gson().toJson(cm));
            });
        }
    }

    private String parseJson(String json, String key) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        return jsonObject.get(key).toString();
    }

    private ChatMessage saveMessage(String message) throws ParseException {
        ChatMessage cm = new ChatMessage();
        cm.setUser(this.uss.getLoggedUser().getName());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = formatter.format(new Date());
        cm.setDate(date);
        cm.setContent(message.replace("\"", ""));
        this.cmr.save(cm);
        return cm;
    }


    private boolean sendMessage(WebSocketSession wss, String message) {
        try {
            wss.sendMessage(new TextMessage(message));
            return true;
        } catch (IOException e) {
            return false;
        }
    }


}

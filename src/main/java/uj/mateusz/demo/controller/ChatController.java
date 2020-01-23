package uj.mateusz.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uj.mateusz.demo.dto.ChatMessageJSON;
import uj.mateusz.demo.entitiy.ChatMessage;
import uj.mateusz.demo.pojo.Message;
import uj.mateusz.demo.repository.ChatMessageRepository;

@Controller
public class ChatController {
    @Autowired
    ChatMessageRepository chatMessageRepository;


    @CrossOrigin()
    @GetMapping(path = "/chat/all",
            produces = "application/json")
    public @ResponseBody String getAll()  {
        StringBuilder result = new StringBuilder();
        this.chatMessageRepository.findAll().forEach(chatMessage -> result.append(this.chatMessageToJSON(chatMessage)));
        return  "[" + result.toString() + "]";
    }

    private String chatMessageToJSON(ChatMessage chateMessage) {
        return new ChatMessageJSON(chateMessage.getContent(), chateMessage.getDate(), chateMessage.getUser()).toString();
    }
}

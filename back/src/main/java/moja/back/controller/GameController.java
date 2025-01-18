package moja.back.controller;

import moja.back.entity.GameMessage;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {

    @MessageMapping("/sendMessage")
    @SendTo("/topic/{roomId}")
    public GameMessage sendMessage(@DestinationVariable String roomId, GameMessage message) {
        // 메시지 처리 로직 추가 가능
        return message;
    }

    @MessageMapping("/joinRoom")
    @SendTo("/topic/{roomId}")
    public String joinRoom(@DestinationVariable String roomId, @Payload String username) {
        // 방 참여자 추가 로직
        return username + " has joined the room!";
    }
}

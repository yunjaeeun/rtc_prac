package moja.socket_pjt.domain.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moja.socket_pjt.domain.chat.model.Message;
import moja.socket_pjt.domain.chat.service.ChatServiceV1;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
@Slf4j
public class WssControllerV1 {

    private final ChatServiceV1 chatServiceV1;

    @MessageMapping("/chat/message/{from}")
    @SendTo("/sub/chat")
    public Message receivedMessage(
            @DestinationVariable String from,
            Message msg
    ) {
        log.info("Message received -> From: {}, to: {}, msg: {}", from, msg.getTo(), msg.getFrom());
        chatServiceV1.saveChatMessage(msg);

        return msg;
    }
}

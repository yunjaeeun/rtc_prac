package moja.back.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Controller
public class VideoRoomController {
    @MessageMapping("/signal")
    @SendTo("/topic/signaling")
    public String relaySignal(String message) {
        return message;
    }
}

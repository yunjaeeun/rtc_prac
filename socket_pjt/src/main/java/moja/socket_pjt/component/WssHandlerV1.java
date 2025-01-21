package moja.socket_pjt.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import moja.socket_pjt.domain.chat.model.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class WssHandlerV1 extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage msg) {
        try {
            String payload = msg.getPayload();
            Message message = objectMapper.readValue(payload, Message.class);

            session.sendMessage(new TextMessage(payload));
        } catch (Exception e) {

        }
    }
}

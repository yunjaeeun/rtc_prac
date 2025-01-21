package moja.socket_pjt.domain.chat.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import moja.socket_pjt.domain.chat.model.Message;

import java.util.List;

@Schema(description = "Chatting List")
public record ChatListResponse(
        @Schema(description = "return Message: []")
        List<Message> result
) {
}

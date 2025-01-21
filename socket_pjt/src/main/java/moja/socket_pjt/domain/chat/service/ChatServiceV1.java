package moja.socket_pjt.domain.chat.service;

import lombok.RequiredArgsConstructor;
import moja.socket_pjt.domain.chat.model.Message;
import moja.socket_pjt.domain.chat.model.response.ChatListResponse;
import moja.socket_pjt.domain.repository.ChatRepository;
import moja.socket_pjt.domain.repository.entity.Chat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatServiceV1 {

    private final ChatRepository chatRepository;

    public ChatListResponse chatList(String from, String to) {
        List<Chat> chats = chatRepository.findTop10BySenderOrReceiverOrderByTIDDesc(from, to);

        List<Message> res = chats.stream()
                .map(chat -> new Message(chat.getReceiver(), chat.getSender(), chat.getMessage()))
                .collect(Collectors.toList());

        return new ChatListResponse(res);
    }

    @Transactional(transactionManager = "createUserTransactionManager")
    public void saveChatMessage(Message msg) {
        Chat chat = Chat.builder()
                .sender(msg.getFrom())
                .receiver(msg.getTo())
                .message(msg.getMessage())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();

        chatRepository.save(chat);
    }

}

package com.example.appchat.controller;

import com.example.appchat.entity.Chat;
import com.example.appchat.entity.Message;
import com.example.appchat.entity.User;
import com.example.appchat.payload.MessageDTO;
import com.example.appchat.repository.ChatRepository;
import com.example.appchat.repository.MessageRepository;
import com.example.appchat.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {


    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("send-message")
    public MessageDTO sendMessageByTopic(@Payload MessageDTO messageDTO) {
        Objects.requireNonNull(messageDTO);

        Optional<Chat> optionalChat = chatRepository
                .findFirstByIdOrFirstSide_UsernameAndSecondSide_UsernameOrSecondSide_UsernameAndFirstSide_Username(
                        messageDTO.getId(),
                        messageDTO.getUsername(),
                        messageDTO.getReceiverUsername(),
                        messageDTO.getReceiverUsername(),
                        messageDTO.getUsername()
                );
        User firstSide;
        User secondSide;
        Chat chat;
        if (optionalChat.isEmpty()) {
            firstSide = userRepository
                    .findByUsername(messageDTO.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Bunday user %s mavjud emas", messageDTO.getUsername())));
            secondSide = userRepository
                    .findByUsername(messageDTO.getReceiverUsername())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Bunday user %s mavjud emas", messageDTO.getReceiverUsername())));
            chat = new Chat(firstSide, secondSide);
            chatRepository.save(chat);
        } else
            chat = optionalChat.get();

        firstSide = chat.getFirstSide();
        secondSide = chat.getSecondSide();

        Message message = new Message(
                messageDTO.getContent(),
                chat,
                Objects.equals(firstSide.getUsername(), messageDTO.getUsername()) ?
                        firstSide : secondSide);
        messageRepository.save(message);

        messageDTO.setId(message.getId());
        messagingTemplate.convertAndSend("/telegram/" +
                        ((Objects.equals(firstSide.getUsername(), messageDTO.getUsername())) ?
                                secondSide.getUsername() : firstSide.getUsername()),
                messageDTO);

        return messageDTO;
    }

    @GetMapping("/list/{chatId}")
    public HttpEntity<?> getMessagesByChatId(@PathVariable Long chatId) {
        return ResponseEntity.ok(messageRepository.findAllByChatId(chatId));
    }
}

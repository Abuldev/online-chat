package com.example.appchat.controller;

import com.example.appchat.entity.Chat;
import com.example.appchat.payload.ChatDTO;
import com.example.appchat.repository.ChatRepository;
import com.example.appchat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @GetMapping("/chats")
    public HttpEntity<?> chats(@RequestHeader("username") String username) {

        Long id = userRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new).getId();

        List<Chat> chats = chatRepository.findAllByFirstSideIdOrSecondSideId(id, id);

        return ResponseEntity.ok(mapChatListDTOForLocal(chats, id));
    }

    public List<ChatDTO> mapChatListDTOForLocal(List<Chat> chats, Long id) {
        return chats
                .stream()
                .map(chat -> new ChatDTO(
                        Objects.equals(chat.getFirstSide().getId(), id) ?
                                chat.getSecondSide().getUsername() :
                                chat.getFirstSide().getUsername()
                        , chat.getId()))
                .collect(Collectors.toList());
    }
}

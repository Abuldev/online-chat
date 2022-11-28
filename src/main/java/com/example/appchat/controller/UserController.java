package com.example.appchat.controller;

import com.example.appchat.entity.Chat;
import com.example.appchat.entity.User;
import com.example.appchat.payload.ChatDTO;
import com.example.appchat.payload.SearchingDTO;
import com.example.appchat.payload.UserDTO;
import com.example.appchat.repository.ChatRepository;
import com.example.appchat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    @PostMapping("/sign")
    public HttpEntity<?> sign(@Valid @RequestBody UserDTO userDTO) {
        User user = userRepository
                .findByUsername(userDTO.getUsername())
                .orElseGet(() -> new User(userDTO.getUsername()));
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/search")
    public HttpEntity<?> search(@RequestHeader("username") String username, @Valid @RequestBody UserDTO userDTO) {
        //global
        Long id = userRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new).getId();
        List<User> globalUsers = userRepository.findAllByUsernameForGlobal(id, userDTO.getUsername());
        List<ChatDTO> globalChatDTOList = mapChatListDTOForGlobal(globalUsers);

        Page<Chat> localUsersPage = chatRepository
                .findAllByFirstSideUsernameContainsIgnoreCaseOrSecondSideUsernameContainsIgnoreCaseOrderByUpdatedAt(
                        userDTO.getUsername(), userDTO.getUsername(),
                        PageRequest.of(0, 10));
        List<ChatDTO> localChatDTOList = mapChatListDTOForLocal(localUsersPage.getContent());

        return ResponseEntity.ok(SearchingDTO
                .builder()
                .global(globalChatDTOList)
                .local(localChatDTOList)
                .build());
    }

    public List<ChatDTO> mapChatListDTOForGlobal(List<User> users) {
        return users
                .stream()
                .map(user -> new ChatDTO(user.getUsername()))
                .collect(Collectors.toList());
    }

    public List<ChatDTO> mapChatListDTOForLocal(List<Chat> chats) {
        return chats
                .stream()
                .map(chat -> new ChatDTO(chat.getId()))
                .collect(Collectors.toList());
    }

}


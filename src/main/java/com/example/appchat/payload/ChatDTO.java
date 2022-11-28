package com.example.appchat.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatDTO {

    private String username;

    private Long chatId;

    public ChatDTO(String username) {
        this.username = username;
    }

    public ChatDTO(Long chatId) {
        this.chatId = chatId;
    }
}

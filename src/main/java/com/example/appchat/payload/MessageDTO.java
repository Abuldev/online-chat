package com.example.appchat.payload;

import lombok.Data;
import lombok.Getter;

@Data
public class MessageDTO {

    private Long id;

    private Long chatId;

    private String username;

    private String receiverUsername;

    private String content;

}

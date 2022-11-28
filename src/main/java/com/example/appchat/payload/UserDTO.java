package com.example.appchat.payload;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserDTO {

    @NotBlank
    private String username;
}

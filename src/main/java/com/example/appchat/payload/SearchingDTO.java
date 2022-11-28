package com.example.appchat.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class SearchingDTO {
    private List<ChatDTO> global;
    private List<ChatDTO> local;
}

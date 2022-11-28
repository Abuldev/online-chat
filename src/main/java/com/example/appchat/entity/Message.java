package com.example.appchat.entity;

import com.example.appchat.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Message extends AbsEntity {

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;
}

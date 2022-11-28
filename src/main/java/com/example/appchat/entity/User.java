package com.example.appchat.entity;

import com.example.appchat.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(name = "users")
@DynamicInsert
@DynamicUpdate
public class User extends AbsEntity {

    @Column(unique = true,nullable = false)
    private String username;
}

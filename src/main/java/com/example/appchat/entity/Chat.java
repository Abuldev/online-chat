package com.example.appchat.entity;

import com.example.appchat.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Table(uniqueConstraints =
@UniqueConstraint(columnNames = {"first_side_id", "second_side_id"}))
//@NamedEntityGraph(name = "first_side_entity_graph", attributeNodes = @NamedAttributeNode(value = "firstSide"))
//@NamedEntityGraph(name = "second_side_entity_graph", attributeNodes = @NamedAttributeNode(value = "secondSide"))

public class Chat extends AbsEntity {
    @ManyToOne(optional = false)
    private User firstSide;

    @ManyToOne(optional = false)
    private User secondSide;
}

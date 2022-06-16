package com.example.springboot.jwt.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "productions")
public class Product extends BaseEntity {

    private String name;
    private String description;

}

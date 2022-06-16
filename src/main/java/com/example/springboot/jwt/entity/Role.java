package com.example.springboot.jwt.entity;

import com.example.springboot.jwt.entity.enums.Roles;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@TypeAlias("Role")
@AllArgsConstructor
@Document(collection = "roles")
public class Role implements GrantedAuthority {

    @Id
    private Roles name;

    @Override
    public String getAuthority() {
        return this.name.getRole();
    }
}

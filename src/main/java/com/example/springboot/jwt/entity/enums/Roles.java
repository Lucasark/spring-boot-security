package com.example.springboot.jwt.entity.enums;

import com.example.springboot.jwt.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum Roles {
    ROLE_ADMIN(Constants.ROLE_ADMIN),
    ROLE_STAFF(Constants.ROLE_STAFF),
    ROLE_PUBLISHER(Constants.ROLE_PUBLISHER),
    ROLE_VIEWER(Constants.ROLE_VIEWER);

    private final String role;
}

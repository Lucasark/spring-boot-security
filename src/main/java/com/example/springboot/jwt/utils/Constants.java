package com.example.springboot.jwt.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_STAFF = "ROLE_STAFF";
    public static final String ROLE_PUBLISHER = "ROLE_PUBLISHER";
    public static final String ROLE_VIEWER = "ROLE_VIEWER";
}

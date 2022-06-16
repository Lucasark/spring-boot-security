package com.example.springboot.jwt.config.initializer;

import com.example.springboot.jwt.entity.Role;
import com.example.springboot.jwt.entity.enums.Roles;
import com.example.springboot.jwt.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class RoleDataBase implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository repository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Arrays.stream(Roles.values()).iterator().forEachRemaining(roles -> {
            if (repository.findById(roles).isEmpty()) {
                repository.save(Role.builder().name(roles).build());
            }
        });
    }
}

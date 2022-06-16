package com.example.springboot.jwt.repository;

import com.example.springboot.jwt.entity.Role;
import com.example.springboot.jwt.entity.enums.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, Roles> {
}

package com.example.springboot.jwt.repository;

import com.example.springboot.jwt.entity.Customer;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, ObjectId> {

    Optional<Customer> findCustomerByUsername(String userName);
}

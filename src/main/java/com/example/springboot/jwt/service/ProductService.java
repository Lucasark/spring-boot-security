package com.example.springboot.jwt.service;

import com.example.springboot.jwt.entity.Product;
import com.example.springboot.jwt.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;

    public Collection<Product> getAllProducts() {
        return repository.findAll();
    }

    public void addProduct(Product product) {
        repository.findProductByName(product.getName()).ifPresentOrElse(p -> {
            throw new IllegalArgumentException(String.format("Product with name %s already exists", p.getName()));
        }, () -> {
            repository.save(product);
        });
    }

    public void deleteProductById(String id) {
        repository.findById(new ObjectId(id)).ifPresentOrElse(repository::delete, () -> {
            throw new IllegalArgumentException("Product with name already not exists");
        });
    }
}

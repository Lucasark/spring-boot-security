package com.example.springboot.jwt.controller;

import com.example.springboot.jwt.entity.Product;
import com.example.springboot.jwt.entity.enums.Roles;
import com.example.springboot.jwt.service.ProductService;
import com.example.springboot.jwt.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Collection<Product> getProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    @DeleteMapping("{id}")
    public void removeProduct(@PathVariable String id) {
        productService.deleteProductById(id);
    }
}

package com.ttm.demo.controller;

import com.ttm.demo.dao.service.ProductService;
import com.ttm.demo.domaine.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
        Product product = service.getProduct(id);
        return ResponseEntity.ok(product);
    }
}

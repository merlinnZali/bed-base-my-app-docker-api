package com.ttm.demo.dao.service;

import com.ttm.demo.dao.repository.ProductRepository;
import com.ttm.demo.domaine.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
}

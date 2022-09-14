package com.ttm.demo.dao.service;

import com.ttm.demo.domaine.Product;

import java.util.List;

public interface ProductService {

    Product getProduct(Long id);

    List<Product> getProducts();
}

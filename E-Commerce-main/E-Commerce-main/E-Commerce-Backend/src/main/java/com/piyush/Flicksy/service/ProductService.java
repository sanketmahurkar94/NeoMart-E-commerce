package com.piyush.Flicksy.service;

import com.piyush.Flicksy.model.Product;
import com.piyush.Flicksy.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {

        return productRepository.findAll();
    }

    public Product getProductById(Long id) {

        return productRepository.findById(id).orElse(null);
    }

    public Product addProduct(Product product) {

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {

        productRepository.deleteById(id);
    }

    public List<Product> searchByKeyword(String keyword) {

        return productRepository.findByNameContainingIgnoreCase(keyword);
    }
}

package com.example.eventreader.service;

import com.example.eventreader.model.Product;
import com.example.eventreader.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProductsByInsuredId(String insuredId) {
        return productRepository.findByEvent_InsuredId(insuredId);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}

package com.example.eventreader.service;

import com.example.eventreader.model.Product;
import com.example.eventreader.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void getProductsByInsuredId() {
        Product product = new Product(1L,"type",100, LocalDate.now(), LocalDate.now(),null);
        List<Product> products = new ArrayList<>();
        products.add(product);
        when(productRepository.findByEvent_InsuredId("1")).thenReturn(products);
        assertEquals(productService.getProductsByInsuredId("1"), products);
    }

    @Test
    void getAllProducts() {
        Product product = new Product(1L,"type",100, LocalDate.now(), LocalDate.now(),null);
        List<Product> products = new ArrayList<>();
        products.add(product);
        when(productRepository.findAll()).thenReturn(products);
        assertEquals(productService.getAllProducts(), products);
    }
}
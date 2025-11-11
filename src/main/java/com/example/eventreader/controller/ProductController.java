package com.example.eventreader.controller;

import com.example.eventreader.model.Product;
import com.example.eventreader.model.ProductResponse;
import com.example.eventreader.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{insuredId}")
    public ProductResponse getProducts(@PathVariable String insuredId) {
        ProductResponse response =  productService.getProductsGroupedBySourceCompanyAccordingInsuredId(insuredId);
        if (response == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found for insuredId: " + insuredId);
        }
        return response;
    }

    @GetMapping("/")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
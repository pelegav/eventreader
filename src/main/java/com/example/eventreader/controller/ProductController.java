package com.example.eventreader.controller;

import com.example.eventreader.model.Product;
import com.example.eventreader.model.ProductResponse;
import com.example.eventreader.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/{insuredId}")
    public ProductResponse getProductsByInsuredId(@PathVariable String insuredId) {
        logger.info("Received request to get products for insuredId: {}", insuredId);
        ProductResponse response =  productService.getProductsGroupedBySourceCompanyAccordingInsuredId(insuredId);
        if (response == null) {
            logger.warn("No products found for insuredId: {}", insuredId);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found for insuredId: " + insuredId);
        }
        logger.info("Returning products for insuredId: {}", insuredId);
        return response;
    }

    @GetMapping("/")
    public List<Product> getAllProducts() {
        logger.info("Received request to get all products");
        List<Product> products = productService.getAllProducts();
        logger.info("Returning {} products", products.size());
        return products;

    }
}
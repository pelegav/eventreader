package com.example.eventreader.service;

import com.example.eventreader.model.Product;
import com.example.eventreader.model.ProductResponse;
import com.example.eventreader.model.SourceCompanyGroup;
import com.example.eventreader.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public ProductResponse getProductsGroupedBySourceCompanyAccordingInsuredId(String insuredId) {
        List<Product> products = productRepository.findByEvent_InsuredId(insuredId);
        Map<String, List<Product>> groupedBySourceCompany = products.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getEvent().getRequestDetails().getSourceCompany(),
                        Collectors.mapping(
                                p->p,
                                Collectors.toList()
                        )
                ));
        ProductResponse response = new ProductResponse();
        response.setInsuredId(insuredId);

        List<SourceCompanyGroup> groups = groupedBySourceCompany.entrySet().stream()
                .map(entry -> {
                    SourceCompanyGroup group = new SourceCompanyGroup();
                    group.setSourceCompanyName(entry.getKey());
                    group.setProducts(entry.getValue());
                    return group;
                })
                .collect(Collectors.toList());

        response.setSourceCompanies(groups);
        return response;
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}

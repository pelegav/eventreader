package com.example.eventreader.service;

import com.example.eventreader.model.Product;
import com.example.eventreader.model.ProductResponse;
import com.example.eventreader.model.SourceCompanyGroup;
import com.example.eventreader.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse getProductsGroupedBySourceCompanyAccordingInsuredId(String insuredId) {

        List<Product> products = productRepository.findByEvent_InsuredId(insuredId);

        Map<String, List<Product>> groupedBySourceCompany = products.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getEvent().getRequestDetails().getSourceCompany(),
                        Collectors.toList()
                ));

        List<SourceCompanyGroup> groups = groupedBySourceCompany.entrySet().stream()
                .map(sourceCompanyAndProducts -> {
                    SourceCompanyGroup group = new SourceCompanyGroup();
                    group.setSourceCompanyName(sourceCompanyAndProducts.getKey());
                    group.setProducts(sourceCompanyAndProducts.getValue());
                    return group;
                })
                .collect(Collectors.toList());

        if(groups.isEmpty()) {
            return null; // will throw HttpStatus.NOT_FOUND exception
        }
        ProductResponse response = new ProductResponse();
        response.setInsuredId(insuredId);
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

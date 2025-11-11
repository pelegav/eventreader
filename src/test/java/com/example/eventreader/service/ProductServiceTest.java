package com.example.eventreader.service;

import com.example.eventreader.model.*;
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
    void testGetProductsGroupedBySourceCompany() {
        Product product = new Product(1L,"type",100, LocalDate.now(), LocalDate.now(),null);
        List<Product> products = List.of(product);
        RequestDetails requestDetails = new RequestDetails("1","21-21-2021","sourceCompany1",null);
        Event event = new Event("1","type","insured-1",products,requestDetails);
        List<Event> events = new ArrayList<>();
        events.add(event);
        requestDetails.setEvents(events);
        product.setEvent(event);
        List<SourceCompanyGroup> sourceCompanyGroups = new ArrayList<>();
        SourceCompanyGroup sourceCompanyGroup = new SourceCompanyGroup();
        sourceCompanyGroup.setSourceCompanyName("sourceCompany1");
        sourceCompanyGroup.setProducts(products);
        sourceCompanyGroups.add(sourceCompanyGroup);
        ProductResponse response = new ProductResponse();
        response.setInsuredId("1");
        response.setSourceCompanies(sourceCompanyGroups);
        when(productRepository.findByEvent_InsuredId("1")).thenReturn(products);
        ProductResponse actual = productService.getProductsGroupedBySourceCompanyAccordingInsuredId("1");
        assertEquals(actual.getInsuredId(), response.getInsuredId());
        assertEquals(actual.getSourceCompanies().get(0).getProducts().get(0).getId(), response.getSourceCompanies().get(0).getProducts().get(0).getId());
    }

    @Test
    void testGetAllProductsTest() {
        Product product = new Product(1L,"type",100, LocalDate.now(), LocalDate.now(),null);
        List<Product> products = List.of(product);
        when(productRepository.findAll()).thenReturn(products);
        assertEquals(productService.getAllProducts(), products);
    }
}